package com.example.digital_payment.payment.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.digital_payment.payment.application.port.in.InitiatePaymentUseCase;
import com.example.digital_payment.payment.application.port.in.LoadCreditCardUseCase;
import com.example.digital_payment.payment.application.port.in.LoadCreditCardsUseCase;
import com.example.digital_payment.payment.application.port.in.LoadTransactionByUserIdAndKeyUseCase;
import com.example.digital_payment.payment.application.port.in.PaymentCreatedUseCase;
import com.example.digital_payment.payment.application.port.in.PaymentCustomerCreateUseCase;
import com.example.digital_payment.payment.application.port.in.PaymentFailedUseCase;
import com.example.digital_payment.payment.application.port.in.RegisterCreditCardUseCase;
import com.example.digital_payment.payment.application.port.out.LoadBillPaymentByTransactionIdPort;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardPort;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardsPort;
import com.example.digital_payment.payment.application.port.out.LoadPaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.LoadTransactionByUserIdAndKeyPort;
import com.example.digital_payment.payment.application.port.out.LoadTransactionPort;
import com.example.digital_payment.payment.application.port.out.PaymentCustomerGatewayPort;
import com.example.digital_payment.payment.application.port.out.PaymentGatewayPort;
import com.example.digital_payment.payment.application.port.out.SaveBillPaymentPort;
import com.example.digital_payment.payment.application.port.out.SaveCreditCardPort;
import com.example.digital_payment.payment.application.port.out.SavePaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.SaveTransactionPort;
import com.example.digital_payment.payment.application.port.out.UpdateBillPaymentPort;
import com.example.digital_payment.payment.application.port.out.UpdateTransactionPort;
import com.example.digital_payment.payment.application.usecase.InitiatePaymentService;
import com.example.digital_payment.payment.application.usecase.LoadCreditCardService;
import com.example.digital_payment.payment.application.usecase.LoadCreditCardsService;
import com.example.digital_payment.payment.application.usecase.LoadTransactionByUserIdAndKeyService;
import com.example.digital_payment.payment.application.usecase.PaymentCreationHandler;
import com.example.digital_payment.payment.application.usecase.PaymentCustomerCreateHandler;
import com.example.digital_payment.payment.application.usecase.PaymentFailureHandler;
import com.example.digital_payment.payment.application.usecase.RegisterCreditCardService;
import com.example.digital_payment.shared.application.port.out.EventPublisherPort;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

@Configuration
public class PaymentConfig {
    @Bean
    public LoadCreditCardUseCase loadCreditCardUseCase(LoadCreditCardPort loadCreditCardPort) {
        return new LoadCreditCardService(loadCreditCardPort);
    }

    @Bean
    public LoadCreditCardsUseCase loadCreditCardsUseCase(LoadCreditCardsPort loadCreditCardsPort) {
        return new LoadCreditCardsService(loadCreditCardsPort);
    }

    @Bean
    public RegisterCreditCardUseCase registerCreditCardUseCase(
        SaveCreditCardPort saveCreditCardPort) {
        return new RegisterCreditCardService(saveCreditCardPort);
    }

    @Bean
    public LoadTransactionByUserIdAndKeyUseCase loadTransactionByUserIdAndKeyUseCase(
        LoadTransactionByUserIdAndKeyPort loadTransactionByUserIdAndKeyPort) {
        return new LoadTransactionByUserIdAndKeyService(loadTransactionByUserIdAndKeyPort);
    }

    @Bean
    public InitiatePaymentUseCase initiatePaymentUseCase(TransactionPort transactionPort,
        SaveTransactionPort saveTransactionPort, SaveBillPaymentPort saveBillPaymentPort,
        LoadCreditCardPort loadCreditCardPort, LoadPaymentCustomerPort loadPaymentCustomerPort,
        PaymentGatewayPort paymentGatewayPort, EventPublisherPort eventPublisher) {
        return new InitiatePaymentService(transactionPort, saveTransactionPort, saveBillPaymentPort,
            loadCreditCardPort, loadPaymentCustomerPort, paymentGatewayPort, eventPublisher);
    }

    @Bean
    public PaymentFailedUseCase paymentFailedUseCase(TransactionPort transactionPort,
        LoadTransactionPort loadTransactionPort, UpdateBillPaymentPort updateBillPaymentPort,
        UpdateTransactionPort updateTransactionPort,
        LoadBillPaymentByTransactionIdPort loadBillPaymentByTransactionIdPort) {
        return new PaymentFailureHandler(loadTransactionPort, loadBillPaymentByTransactionIdPort,
            transactionPort, updateBillPaymentPort, updateTransactionPort);
    }

    @Bean
    public PaymentCreatedUseCase paymentCreatedUseCase(TransactionPort transactionPort,
        LoadTransactionPort loadTransactionPort, UpdateTransactionPort updateTransactionPort) {
        return new PaymentCreationHandler(transactionPort, loadTransactionPort,
            updateTransactionPort);
    }

    @Bean
    PaymentCustomerCreateUseCase paymentCustomerCreateUseCase(
        PaymentCustomerGatewayPort paymentCustomerGatewayPort,
        SavePaymentCustomerPort savePaymentCustomerPort) {
        return new PaymentCustomerCreateHandler(paymentCustomerGatewayPort,
            savePaymentCustomerPort);
    }
}
