package com.example.digital_payment.payment.api.facade;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.api.dto.request.PaymentRequest;
import com.example.digital_payment.payment.api.dto.response.PaymentResponse;
import com.example.digital_payment.payment.api.mapper.PaymentApiMapper;
import com.example.digital_payment.payment.application.dto.InitiatePaymentCommand;
import com.example.digital_payment.payment.application.port.in.InitiatePaymentUseCase;
import com.example.digital_payment.payment.application.port.in.LoadTransactionByUserIdAndKeyUseCase;
import com.example.digital_payment.payment.application.port.out.IdempotencyStore;
import com.example.digital_payment.payment.domain.exceptions.InvalidPaymentRequestException;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.shared.application.port.in.FindPayableBillUseCase;
import com.example.digital_payment.shared.dto.BillInfo;
import com.example.digital_payment.shared.security.CurrentUserContext;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PaymentFacade {
    private final CurrentUserContext currentUserContext;
    private final FindPayableBillUseCase findPayableBillUseCase;
    private final IdempotencyStore idempotencyStore;
    private final PaymentApiMapper paymentApiMapper;
    private final LoadTransactionByUserIdAndKeyUseCase loadTransactionByUserIdAndKeyUseCase;
    private final InitiatePaymentUseCase initiatePaymentUseCase;

    public PaymentFacade(CurrentUserContext currentUserContext,
        FindPayableBillUseCase findPayableBillUseCase, IdempotencyStore idempotencyStore,
        PaymentApiMapper paymentApiMapper,
        LoadTransactionByUserIdAndKeyUseCase loadTransactionByUserIdAndKeyUseCase,
        InitiatePaymentUseCase initiatePaymentUseCase) {
        this.currentUserContext = currentUserContext;
        this.findPayableBillUseCase = findPayableBillUseCase;
        this.idempotencyStore = idempotencyStore;
        this.paymentApiMapper = paymentApiMapper;
        this.loadTransactionByUserIdAndKeyUseCase = loadTransactionByUserIdAndKeyUseCase;
        this.initiatePaymentUseCase = initiatePaymentUseCase;
    }

    public PaymentResponse initiatePayment(PaymentRequest request, UUID idempotencyKey) {
        Optional<BillInfo> existingBill = findPayableBillUseCase.fetchBillInfo(request.billId());
        if (existingBill.isEmpty()) {
            log.error("Payment initiation failed. bill with id {} was not found.",
                request.billId());
            throw new InvalidPaymentRequestException(
                "Cannot initiate payment for non-existing bill");
        }
        UUID userId = currentUserContext.getUserId();
        BillInfo billInfo = existingBill.get();

        Optional<Transactions> cached = idempotencyStore.get(userId, idempotencyKey);
        if (!cached.isEmpty()) {
            log.debug("Transaction found at cache level, returning the cached value to user.");
            return paymentApiMapper.toResponse(cached.get());
        }

        Optional<Transactions> existing = loadTransactionByUserIdAndKeyUseCase
            .loadTransactionByUserIdAndKey(userId, idempotencyKey);
        if (!existing.isEmpty()) {
            log.debug("Transaction found at DB level, returning the value to user.");
            return paymentApiMapper.toResponse(existing.get());
        }

        InitiatePaymentCommand command = new InitiatePaymentCommand(userId, idempotencyKey,
            request.billId(), request.creditCardId(), billInfo.currency(), billInfo.amount());
        log.debug("Initiating payment data: {}", command);
        Transactions tx = initiatePaymentUseCase.initiatePayment(command);

        idempotencyStore.save(userId, idempotencyKey, tx);

        return paymentApiMapper.toResponse(tx);
    }
}
