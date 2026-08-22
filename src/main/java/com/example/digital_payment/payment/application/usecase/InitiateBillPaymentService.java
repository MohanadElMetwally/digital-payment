package com.example.digital_payment.payment.application.usecase;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import com.example.digital_payment.payment.application.dto.CreatePaymentCommand;
import com.example.digital_payment.payment.application.dto.FundingPlan;
import com.example.digital_payment.payment.application.dto.InitiatePaymentCommand;
import com.example.digital_payment.payment.application.dto.PaymentInitiationCreatedEvent;
import com.example.digital_payment.payment.application.dto.PaymentInitiationFailedEvent;
import com.example.digital_payment.payment.application.dto.PaymentInitiationResult;
import com.example.digital_payment.payment.application.dto.PaymentSucceededEvent;
import com.example.digital_payment.payment.application.exception.PaymentProviderException;
import com.example.digital_payment.payment.application.port.in.InitiateBillPaymentUseCase;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardPort;
import com.example.digital_payment.payment.application.port.out.LoadPaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.PaymentGatewayPort;
import com.example.digital_payment.payment.application.port.out.SaveTransactionPort;
import com.example.digital_payment.payment.domain.exceptions.CardAccessDeniedException;
import com.example.digital_payment.payment.domain.exceptions.CardNotFoundException;
import com.example.digital_payment.payment.domain.exceptions.InvalidPaymentRequestException;
import com.example.digital_payment.payment.domain.exceptions.PaymentCustomerNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;
import com.example.digital_payment.payment.domain.model.entities.PaymentCustomers;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.payment.domain.model.valueobjects.TransactionCreationData;
import com.example.digital_payment.shared.application.port.in.FindWalletInfoUseCase;
import com.example.digital_payment.shared.application.port.out.EventPublisherPort;
import com.example.digital_payment.shared.application.port.out.TransactionPort;
import com.example.digital_payment.shared.dto.WalletInfo;
import com.example.digital_payment.shared.events.DebitWalletEvent;
import com.example.digital_payment.shared.events.InitiateBillPaymentEvent;
import com.example.digital_payment.shared.events.InitiateBillPaymentFailedEvent;
import com.example.digital_payment.shared.events.InitiateWalletDebitTransactionEvent;
import com.example.digital_payment.shared.events.WalletTransactionFailedEvent;
import com.example.digital_payment.shared.exception.ForbiddenException;
import com.example.digital_payment.shared.exception.ResourceNotFoundException;

public class InitiateBillPaymentService implements InitiateBillPaymentUseCase {
    private final TransactionPort transactionPort;
    private final SaveTransactionPort saveTransactionPort;
    private final LoadCreditCardPort loadCreditCardPort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final EventPublisherPort eventPublisher;
    private final LoadPaymentCustomerPort loadPaymentCustomerPort;
    private final FindWalletInfoUseCase findWalletInfoUseCase;

    public InitiateBillPaymentService(TransactionPort transactionPort,
            SaveTransactionPort saveTransactionPort, LoadCreditCardPort loadCreditCardPort,
            LoadPaymentCustomerPort loadPaymentCustomerPort, PaymentGatewayPort paymentGatewayPort,
            EventPublisherPort eventPublisher, FindWalletInfoUseCase findWalletInfoUseCase) {
        this.transactionPort = transactionPort;
        this.saveTransactionPort = saveTransactionPort;
        this.loadCreditCardPort = loadCreditCardPort;
        this.loadPaymentCustomerPort = loadPaymentCustomerPort;
        this.paymentGatewayPort = paymentGatewayPort;
        this.eventPublisher = eventPublisher;
        this.findWalletInfoUseCase = findWalletInfoUseCase;
    }

    @Override
    public Transactions initiatePayment(InitiatePaymentCommand command) {
        FundingPlan plan = resolveFundingPlan(command);
        Transactions tx = createAndFundTransaction(command, plan);

        if (plan.isFullyCoveredByWallet()) {
            return completeFromWallet(tx, command.referenceId(), plan.walletInfo());
        }
        return chargeCardForRemainder(command, tx, plan);
    }

    private FundingPlan resolveFundingPlan(InitiatePaymentCommand command) {
        if (!command.useWallet()) {
            return FundingPlan.cardOnly(command.amount());
        }

        WalletInfo wallet;
        try {
            wallet = findWalletInfoUseCase.fetchWalletInfo(command.walletId(), command.userId());
        } catch (ResourceNotFoundException e) {
            throw new InvalidPaymentRequestException(
                    "Cannot initiate payment with non-existing wallet");
        } catch (ForbiddenException e) {
            throw new InvalidPaymentRequestException(
                    "Not authorized for this wallet: " + e.getMessage());
        }

        BigDecimal walletAmount = wallet.balance().min(command.amount());
        BigDecimal cardAmount = command.amount().subtract(walletAmount);
        return new FundingPlan(walletAmount, cardAmount, wallet);
    }

    private Transactions createAndFundTransaction(InitiatePaymentCommand command,
            FundingPlan plan) {
        TransactionCreationData data =
                new TransactionCreationData(command.userId(), command.idempotencyKey(),
                        command.type(), command.amount(), command.currency(), null);
        Transactions tx = Transactions.create(data);

        transactionPort.executeVoid(() -> {
            saveTransactionPort.save(tx);
            if (plan.walletAmount().signum() > 0) {
                eventPublisher.publish(new InitiateWalletDebitTransactionEvent(command.walletId(),
                        tx.getId(), command.amount()));
            }
            eventPublisher.publish(new InitiateBillPaymentEvent(command.referenceId(), tx.getId()));
        });
        return tx;
    }

    private Transactions completeFromWallet(Transactions tx, UUID referenceId,
            WalletInfo walletInfo) {
        transactionPort.executeVoid(() -> {
            eventPublisher.publish(new DebitWalletEvent(tx.getUserId(), walletInfo.walletId(),
                    tx.getId(), tx.getAmount()));
            eventPublisher.publish(new PaymentSucceededEvent(tx.getId(), "WALLET", referenceId));
        });
        return tx;
    }

    private Transactions chargeCardForRemainder(InitiatePaymentCommand command, Transactions tx,
            FundingPlan plan) {
        CreditCards card = getAuthorizedCard(command.userId(), command.creditCardId());
        String customerId = getPaymentCustomerId(command.userId());

        CreatePaymentCommand paymentCommand = new CreatePaymentCommand(tx.getId(), customerId,
                plan.cardAmount(), command.currency(), card.getPaymentMethodId(),
                command.idempotencyKey(), command.referenceId());

        PaymentInitiationResult result;
        try {
            result = paymentGatewayPort.createPayment(paymentCommand);
        } catch (PaymentProviderException ex) {
            transactionPort.executeVoid(() -> {
                if (plan.walletAmount().signum() > 0) {
                    eventPublisher.publish(new WalletTransactionFailedEvent(tx.getId()));
                }
                eventPublisher
                        .publish(new PaymentInitiationFailedEvent(tx.getId(), ex.getMessage()));
                eventPublisher.publish(
                        new InitiateBillPaymentFailedEvent(command.referenceId(), tx.getId()));
            });
            throw ex;
        }

        transactionPort.executeVoid(() -> {
            if (plan.walletAmount().signum() > 0) {
                eventPublisher.publish(new DebitWalletEvent(tx.getUserId(),
                        plan.walletInfo().walletId(), tx.getId(), plan.walletAmount()));
            }
            eventPublisher.publish(
                    new PaymentInitiationCreatedEvent(tx.getId(), result.externalReference()));
        });
        return tx;
    }

    private CreditCards getAuthorizedCard(UUID userId, UUID cardId) {
        Optional<CreditCards> existingCard = loadCreditCardPort.findById(cardId);

        if (existingCard.isEmpty()) {
            throw new CardNotFoundException("CreditCard was not found");
        }

        CreditCards card = existingCard.get();
        if (!card.belongsTo(userId)) {
            throw new CardAccessDeniedException("Access to credit card denied");
        }
        return card;
    }

    private String getPaymentCustomerId(UUID userId) {
        PaymentCustomers customer = loadPaymentCustomerPort.findByUserId(userId)
                .orElseThrow(() -> new PaymentCustomerNotFoundException(
                        "Provider Customer not found for this user"));
        return customer.getCustomerId();
    }
}
