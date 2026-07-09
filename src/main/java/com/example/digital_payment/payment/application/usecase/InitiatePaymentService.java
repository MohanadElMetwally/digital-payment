package com.example.digital_payment.payment.application.usecase;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.payment.application.dto.CreatePaymentCommand;
import com.example.digital_payment.payment.application.dto.InitiatePaymentCommand;
import com.example.digital_payment.payment.application.dto.PaymentInitiationCreatedEvent;
import com.example.digital_payment.payment.application.dto.PaymentInitiationFailedEvent;
import com.example.digital_payment.payment.application.dto.PaymentInitiationResult;
import com.example.digital_payment.payment.application.exception.PaymentProviderException;
import com.example.digital_payment.payment.application.port.in.InitiatePaymentUseCase;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardPort;
import com.example.digital_payment.payment.application.port.out.LoadPaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.PaymentGatewayPort;
import com.example.digital_payment.payment.application.port.out.SaveBillPaymentPort;
import com.example.digital_payment.payment.application.port.out.SaveTransactionPort;
import com.example.digital_payment.payment.domain.enums.TransactionType;
import com.example.digital_payment.payment.domain.exceptions.CardAccessDeniedException;
import com.example.digital_payment.payment.domain.exceptions.CardNotFoundException;
import com.example.digital_payment.payment.domain.exceptions.PaymentCustomerNotFoundException;
import com.example.digital_payment.payment.domain.model.entities.BillPayments;
import com.example.digital_payment.payment.domain.model.entities.CreditCards;
import com.example.digital_payment.payment.domain.model.entities.PaymentCustomers;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.payment.domain.model.valueobjects.BillPaymentCreationData;
import com.example.digital_payment.payment.domain.model.valueobjects.TransactionCreationData;
import com.example.digital_payment.shared.application.port.out.EventPublisherPort;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

public class InitiatePaymentService implements InitiatePaymentUseCase {
    private final TransactionPort transactionPort;
    private final SaveTransactionPort saveTransactionPort;
    private final SaveBillPaymentPort saveBillPaymentPort;
    private final LoadCreditCardPort loadCreditCardPort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final EventPublisherPort eventPublisher;
    private final LoadPaymentCustomerPort loadPaymentCustomerPort;

    public InitiatePaymentService(TransactionPort transactionPort,
        SaveTransactionPort saveTransactionPort, SaveBillPaymentPort saveBillPaymentPort,
        LoadCreditCardPort loadCreditCardPort, LoadPaymentCustomerPort loadPaymentCustomerPort,
        PaymentGatewayPort paymentGatewayPort, EventPublisherPort eventPublisher) {
        this.transactionPort = transactionPort;
        this.saveTransactionPort = saveTransactionPort;
        this.saveBillPaymentPort = saveBillPaymentPort;
        this.loadCreditCardPort = loadCreditCardPort;
        this.loadPaymentCustomerPort = loadPaymentCustomerPort;
        this.paymentGatewayPort = paymentGatewayPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Transactions initiatePayment(InitiatePaymentCommand command) {
        Transactions tx = createAndPersistTransaction(command);
        CreditCards card = getAuthorizedCard(command.userId(), command.creditCardId());
        String customerId = getPaymentCustomerId(command.userId());

        CreatePaymentCommand paymentCommand = new CreatePaymentCommand(tx.getId(), customerId,
            command.amount(), command.currency(), card.getPaymentMethodId(),
            command.idempotencyKey());
        PaymentInitiationResult result;
        try {
            result = paymentGatewayPort.createPayment(paymentCommand);
        } catch (PaymentProviderException ex) {
            transactionPort.executeVoid(() -> {
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

    private Transactions createAndPersistTransaction(InitiatePaymentCommand command) {
        TransactionCreationData transactionCreationData = new TransactionCreationData(
            command.userId(), command.idempotencyKey(), TransactionType.BILL_PAYMENT,
            command.amount(), command.currency(), null);
        Transactions tx = Transactions.create(transactionCreationData);

        BillPaymentCreationData billPaymentCreationData = new BillPaymentCreationData(tx.getId(),
            command.billId(), command.amount());
        BillPayments billPayment = BillPayments.create(billPaymentCreationData);

        transactionPort.executeVoid(() -> {
            saveTransactionPort.save(tx);
            saveBillPaymentPort.save(billPayment);
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
