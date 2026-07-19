package com.example.digital_payment.payment.api.facade;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.api.dto.request.PaymentRequest;
import com.example.digital_payment.payment.api.dto.request.WalletTopUpRequest;
import com.example.digital_payment.payment.api.dto.response.PaymentResponse;
import com.example.digital_payment.payment.api.mapper.PaymentApiMapper;
import com.example.digital_payment.payment.application.dto.InitiatePaymentCommand;
import com.example.digital_payment.payment.application.port.in.InitiateBillPaymentUseCase;
import com.example.digital_payment.payment.application.port.in.InitiateWalletTopUpUseCase;
import com.example.digital_payment.payment.application.port.in.LoadTransactionByUserIdAndKeyUseCase;
import com.example.digital_payment.payment.application.port.out.TransactionCacheStore;
import com.example.digital_payment.payment.domain.enums.TransactionType;
import com.example.digital_payment.payment.domain.exceptions.InvalidPaymentRequestException;
import com.example.digital_payment.payment.domain.model.entities.Transactions;
import com.example.digital_payment.shared.application.port.in.FindPayableBillUseCase;
import com.example.digital_payment.shared.application.port.in.FindWalletInfoUseCase;
import com.example.digital_payment.shared.dto.BillInfo;
import com.example.digital_payment.shared.dto.WalletInfo;
import com.example.digital_payment.shared.exception.ForbiddenException;
import com.example.digital_payment.shared.exception.ResourceNotFoundException;
import com.example.digital_payment.shared.security.CurrentUserContext;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PaymentFacade {
    private final CurrentUserContext currentUserContext;
    private final FindPayableBillUseCase findPayableBillUseCase;
    private final TransactionCacheStore transactionCacheStore;
    private final PaymentApiMapper paymentApiMapper;
    private final LoadTransactionByUserIdAndKeyUseCase loadTransactionByUserIdAndKeyUseCase;
    private final InitiateBillPaymentUseCase initiatePaymentUseCase;
    private final FindWalletInfoUseCase findWalletInfoUseCase;
    private final InitiateWalletTopUpUseCase initiateWalletTopUpUseCase;

    public PaymentFacade(CurrentUserContext currentUserContext,
        FindPayableBillUseCase findPayableBillUseCase, TransactionCacheStore transactionCacheStore,
        PaymentApiMapper paymentApiMapper,
        LoadTransactionByUserIdAndKeyUseCase loadTransactionByUserIdAndKeyUseCase,
        InitiateBillPaymentUseCase initiatePaymentUseCase,
        FindWalletInfoUseCase findWalletInfoUseCase,
        InitiateWalletTopUpUseCase initiateWalletTopUpUseCase) {
        this.currentUserContext = currentUserContext;
        this.findPayableBillUseCase = findPayableBillUseCase;
        this.transactionCacheStore = transactionCacheStore;
        this.paymentApiMapper = paymentApiMapper;
        this.loadTransactionByUserIdAndKeyUseCase = loadTransactionByUserIdAndKeyUseCase;
        this.initiatePaymentUseCase = initiatePaymentUseCase;
        this.findWalletInfoUseCase = findWalletInfoUseCase;
        this.initiateWalletTopUpUseCase = initiateWalletTopUpUseCase;
    }

    public PaymentResponse initiateBillPayment(PaymentRequest request, UUID idempotencyKey) {
        BillInfo billInfo = findPayableBillUseCase.fetchBillInfo(request.billId())
            .orElseThrow(() -> {
                log.error("Payment initiation failed. bill with id {} was not found",
                    request.billId());
                return new InvalidPaymentRequestException(
                    "Cannot initiate payment for non-existing bill");
            });

        UUID userId = currentUserContext.getUserId();

        if (request.useWallet()) {
            try {
                findWalletInfoUseCase.fetchWalletInfo(request.walletId(), userId);
            } catch (ResourceNotFoundException e) {
                log.error("Wallet top-up initiation failed, wallet {} not found",
                    request.walletId());
                throw new InvalidPaymentRequestException(
                    "Cannot initiate top-up for non-existing wallet");
            } catch (ForbiddenException e) {
                log.warn("Wallet top-up denied: wallet {} does not belong to user {}",
                    request.walletId(), userId);
                throw new InvalidPaymentRequestException("Not authorized for this wallet");
            }
        }

        Optional<Transactions> cached = transactionCacheStore.get(userId, idempotencyKey);
        if (cached.isPresent()) {
            log.debug("Transaction found at cache level, returning the cached value to user.");
            return paymentApiMapper.toResponse(cached.get());
        }

        Optional<Transactions> existing = loadTransactionByUserIdAndKeyUseCase
            .loadTransactionByUserIdAndKey(userId, idempotencyKey);
        if (existing.isPresent()) {
            log.debug("Transaction found at DB level, returning the value to user.");
            transactionCacheStore.save(userId, idempotencyKey, existing.get());
            return paymentApiMapper.toResponse(existing.get());
        }

        InitiatePaymentCommand command = new InitiatePaymentCommand(userId, idempotencyKey,
            request.creditCardId(), TransactionType.PAYMENT, billInfo.currency(), billInfo.amount(),
            request.billId(), request.useWallet(), request.walletId());
        log.debug("Initiating payment data: {}", command);
        Transactions tx = initiatePaymentUseCase.initiatePayment(command);

        transactionCacheStore.save(userId, idempotencyKey, tx);

        return paymentApiMapper.toResponse(tx);
    }

    public PaymentResponse initiateWalletTopUp(WalletTopUpRequest request, UUID idempotencyKey) {
        UUID userId = currentUserContext.getUserId();
        WalletInfo walletInfo;
        try {
            walletInfo = findWalletInfoUseCase.fetchWalletInfo(request.walletId(), userId);
        } catch (ResourceNotFoundException e) {
            log.error("Wallet top-up initiation failed, wallet {} not found", request.walletId());
            throw new InvalidPaymentRequestException(
                "Cannot initiate top-up for non-existing wallet");
        } catch (ForbiddenException e) {
            log.warn("Wallet top-up denied: wallet {} does not belong to user {}",
                request.walletId(), userId);
            throw new InvalidPaymentRequestException(
                "Not authorized for this wallet: " + e.getMessage());
        }

        Optional<Transactions> cached = transactionCacheStore.get(userId, idempotencyKey);
        if (cached.isPresent()) {
            log.debug("Transaction found at cache level, returning the cached value to user.");
            return paymentApiMapper.toResponse(cached.get());
        }

        Optional<Transactions> existing = loadTransactionByUserIdAndKeyUseCase
            .loadTransactionByUserIdAndKey(userId, idempotencyKey);
        if (existing.isPresent()) {
            log.debug("Transaction found at DB level, returning the value to user.");
            transactionCacheStore.save(userId, idempotencyKey, existing.get());
            return paymentApiMapper.toResponse(existing.get());
        }

        InitiatePaymentCommand command = new InitiatePaymentCommand(userId, idempotencyKey,
            request.creditCardId(), TransactionType.WALLET_TOP_UP, walletInfo.currency(),
            request.amount(), request.walletId());
        log.debug("Initiating payment data: {}", command);
        Transactions tx = initiateWalletTopUpUseCase.initiateWalletTopUp(command);

        transactionCacheStore.save(userId, idempotencyKey, tx);

        return paymentApiMapper.toResponse(tx);
    }

    // private PaymentResponse initiate(UUID idempotencyKey,
    // PaymentInitiationContext context) {
    // UUID userId = currentUserContext.getUserId();

    // Optional<Transactions> cached = transactionCacheStore.get(userId,
    // idempotencyKey);
    // if (cached.isPresent()) {
    // log.debug("Transaction found at cache level, returning the cached value to
    // user.");
    // return paymentApiMapper.toResponse(cached.get());
    // }

    // Optional<Transactions> existing = loadTransactionByUserIdAndKeyUseCase
    // .loadTransactionByUserIdAndKey(userId, idempotencyKey);
    // if (existing.isPresent()) {
    // log.debug("Transaction found at DB level, returning the value to user.");
    // transactionCacheStore.save(userId, idempotencyKey, existing.get());
    // return paymentApiMapper.toResponse(existing.get());
    // }

    // InitiatePaymentCommand command = new InitiatePaymentCommand(userId,
    // idempotencyKey,
    // context.creditCardId(), context.type(), context.currency(), context.amount(),
    // context.referenceId(), context.useWallet(), context.walletId());
    // log.debug("Initiating payment data: {}", command);
    // Transactions tx = initiatePaymentUseCase.initiatePayment(command);

    // transactionCacheStore.save(userId, idempotencyKey, tx);

    // return paymentApiMapper.toResponse(tx);
    // }

}
