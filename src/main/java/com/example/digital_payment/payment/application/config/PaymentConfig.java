package com.example.digital_payment.payment.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.digital_payment.payment.application.port.in.InitiateBillPaymentUseCase;
import com.example.digital_payment.payment.application.port.in.InitiateWalletTopUpUseCase;
import com.example.digital_payment.payment.application.port.in.LoadCreditCardUseCase;
import com.example.digital_payment.payment.application.port.in.LoadCreditCardsUseCase;
import com.example.digital_payment.payment.application.port.in.LoadTransactionByUserIdAndKeyUseCase;
import com.example.digital_payment.payment.application.port.in.MarkPaymentFailedUseCase;
import com.example.digital_payment.payment.application.port.in.MarkPaymentSucceededUseCase;
import com.example.digital_payment.payment.application.port.in.PaymentCreatedUseCase;
import com.example.digital_payment.payment.application.port.in.PaymentCustomerCreateUseCase;
import com.example.digital_payment.payment.application.port.in.RegisterCreditCardUseCase;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardPort;
import com.example.digital_payment.payment.application.port.out.LoadCreditCardsPort;
import com.example.digital_payment.payment.application.port.out.LoadPaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.LoadTransactionByUserIdAndKeyPort;
import com.example.digital_payment.payment.application.port.out.LoadTransactionPort;
import com.example.digital_payment.payment.application.port.out.PaymentCustomerGatewayPort;
import com.example.digital_payment.payment.application.port.out.PaymentGatewayPort;
import com.example.digital_payment.payment.application.port.out.RegisterCardGateway;
import com.example.digital_payment.payment.application.port.out.SaveCreditCardPort;
import com.example.digital_payment.payment.application.port.out.SavePaymentCustomerPort;
import com.example.digital_payment.payment.application.port.out.SaveTransactionPort;
import com.example.digital_payment.payment.application.port.out.UpdateTransactionPort;
import com.example.digital_payment.payment.application.usecase.InitiateBillPaymentService;
import com.example.digital_payment.payment.application.usecase.InitiateWalletTopUpService;
import com.example.digital_payment.payment.application.usecase.LoadCreditCardService;
import com.example.digital_payment.payment.application.usecase.LoadCreditCardsService;
import com.example.digital_payment.payment.application.usecase.LoadTransactionByUserIdAndKeyService;
import com.example.digital_payment.payment.application.usecase.MarkPaymentFailedHandler;
import com.example.digital_payment.payment.application.usecase.MarkPaymentSucceededHandler;
import com.example.digital_payment.payment.application.usecase.PaymentCreationHandler;
import com.example.digital_payment.payment.application.usecase.PaymentCustomerCreateHandler;
import com.example.digital_payment.payment.application.usecase.RegisterCreditCardService;
import com.example.digital_payment.shared.application.port.in.FindWalletInfoUseCase;
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
        SaveCreditCardPort saveCreditCardPort, LoadPaymentCustomerPort loadPaymentCustomerPort,
        RegisterCardGateway gateway) {
        return new RegisterCreditCardService(saveCreditCardPort, loadPaymentCustomerPort, gateway);
    }

    @Bean
    public LoadTransactionByUserIdAndKeyUseCase loadTransactionByUserIdAndKeyUseCase(
        LoadTransactionByUserIdAndKeyPort loadTransactionByUserIdAndKeyPort) {
        return new LoadTransactionByUserIdAndKeyService(loadTransactionByUserIdAndKeyPort);
    }

    @Bean
    public InitiateBillPaymentUseCase initiatePaymentUseCase(TransactionPort transactionPort,
        SaveTransactionPort saveTransactionPort, LoadCreditCardPort loadCreditCardPort,
        LoadPaymentCustomerPort loadPaymentCustomerPort, PaymentGatewayPort paymentGatewayPort,
        EventPublisherPort eventPublisher, FindWalletInfoUseCase findWalletInfoUseCase) {
        return new InitiateBillPaymentService(transactionPort, saveTransactionPort,
            loadCreditCardPort, loadPaymentCustomerPort, paymentGatewayPort, eventPublisher,
            findWalletInfoUseCase);
    }

    @Bean
    public InitiateWalletTopUpUseCase initiateWalletTopUpUseCase(TransactionPort transactionPort,
        SaveTransactionPort saveTransactionPort, LoadCreditCardPort loadCreditCardPort,
        LoadPaymentCustomerPort loadPaymentCustomerPort, PaymentGatewayPort paymentGatewayPort,
        EventPublisherPort eventPublisher) {
        return new InitiateWalletTopUpService(transactionPort, saveTransactionPort,
            loadCreditCardPort, loadPaymentCustomerPort, paymentGatewayPort, eventPublisher);
    }

    @Bean
    public MarkPaymentFailedUseCase paymentFailedUseCase(TransactionPort transactionPort,
        LoadTransactionPort loadTransactionPort, UpdateTransactionPort updateTransactionPort) {
        return new MarkPaymentFailedHandler(loadTransactionPort, transactionPort,
            updateTransactionPort);
    }

    @Bean
    public MarkPaymentSucceededUseCase markPaymentSucceededUseCase(
        LoadTransactionPort loadTransactionPort, TransactionPort transactionPort,
        UpdateTransactionPort updateTransactionPort) {
        return new MarkPaymentSucceededHandler(loadTransactionPort, transactionPort,
            updateTransactionPort);
    }

    @Bean
    public PaymentCreatedUseCase paymentCreatedUseCase(TransactionPort transactionPort,
        LoadTransactionPort loadTransactionPort, UpdateTransactionPort updateTransactionPort) {
        return new PaymentCreationHandler(transactionPort, loadTransactionPort,
            updateTransactionPort);
    }

    @Bean
    public PaymentCustomerCreateUseCase paymentCustomerCreateUseCase(
        PaymentCustomerGatewayPort paymentCustomerGatewayPort,
        SavePaymentCustomerPort savePaymentCustomerPort) {
        return new PaymentCustomerCreateHandler(paymentCustomerGatewayPort,
            savePaymentCustomerPort);
    }

}
