package com.example.digital_payment.payment.application.usecase;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.payment.application.dto.CreatePaymentCommand;
import com.example.digital_payment.payment.application.dto.InitiatePaymentCommand;
import com.example.digital_payment.payment.application.dto.PaymentInitiationCreatedEvent;
import com.example.digital_payment.payment.application.dto.PaymentInitiationFailedEvent;
import com.example.digital_payment.payment.application.dto.PaymentInitiationResult;
import com.example.digital_payment.payment.application.exception.PaymentProviderException;
import com.example.digital_payment.payment.application.port.in.InitiateWalletTopUpUseCase;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardPort;
import com.example.digital_payment.payment.application.port.out.LoadPaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.PaymentGatewayPort;
import com.example.digital_payment.payment.application.port.out.SaveTransactionPort;
import com.example.digital_payment.payment.domain.exceptions.CardAccessDeniedException;
import com.example.digital_payment.payment.domain.exceptions.CardNotFoundException;
import com.example.digital_payment.payment.domain.exceptions.PaymentCustomerNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;
import com.example.digital_payment.payment.domain.model.entities.PaymentCustomers;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.payment.domain.model.valueobjects.TransactionCreationData;
import com.example.digital_payment.shared.application.port.out.EventPublisherPort;
import com.example.digital_payment.shared.application.port.out.TransactionPort;
import com.example.digital_payment.shared.events.InitiateWalletCreditTransactionEvent;
import com.example.digital_payment.shared.events.WalletTransactionFailedEvent;

public class InitiateWalletTopUpService implements InitiateWalletTopUpUseCase {
    private final TransactionPort transactionPort;
    private final SaveTransactionPort saveTransactionPort;
    private final LoadCreditCardPort loadCreditCardPort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final EventPublisherPort eventPublisher;
    private final LoadPaymentCustomerPort loadPaymentCustomerPort;

    public InitiateWalletTopUpService(TransactionPort transactionPort,
        SaveTransactionPort saveTransactionPort, LoadCreditCardPort loadCreditCardPort,
        LoadPaymentCustomerPort loadPaymentCustomerPort, PaymentGatewayPort paymentGatewayPort,
        EventPublisherPort eventPublisher) {
        this.transactionPort = transactionPort;
        this.saveTransactionPort = saveTransactionPort;
        this.loadCreditCardPort = loadCreditCardPort;
        this.loadPaymentCustomerPort = loadPaymentCustomerPort;
        this.paymentGatewayPort = paymentGatewayPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Transactions initiateWalletTopUp(InitiatePaymentCommand command) {
        Transactions tx = createTransaction(command);
        return chargeCard(command, tx);
    }

    private Transactions createTransaction(InitiatePaymentCommand command) {
        TransactionCreationData data = new TransactionCreationData(command.userId(),
            command.idempotencyKey(), command.type(), command.amount(), command.currency(), null);
        Transactions tx = Transactions.create(data);

        transactionPort.executeVoid(() -> {
            saveTransactionPort.save(tx);
            eventPublisher.publish(new InitiateWalletCreditTransactionEvent(command.referenceId(),
                tx.getId(), command.amount()));
        });
        return tx;
    }

    private Transactions chargeCard(InitiatePaymentCommand command, Transactions tx) {
        CreditCards card = getAuthorizedCard(command.userId(), command.creditCardId());
        String customerId = getPaymentCustomerId(command.userId());

        CreatePaymentCommand paymentCommand = new CreatePaymentCommand(tx.getId(), customerId,
            command.amount(), command.currency(), card.getPaymentMethodId(),
            command.idempotencyKey(), command.referenceId());

        PaymentInitiationResult result;
        try {
            result = paymentGatewayPort.createPayment(paymentCommand);
        } catch (PaymentProviderException ex) {
            transactionPort.executeVoid(() -> {
                eventPublisher.publish(new WalletTransactionFailedEvent(tx.getId()));
                eventPublisher
                    .publish(new PaymentInitiationFailedEvent(tx.getId(), ex.getMessage()));
            });
            throw ex;
        }

        transactionPort.executeVoid(() -> {
            eventPublisher
                .publish(new PaymentInitiationCreatedEvent(tx.getId(), result.externalReference()));
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
