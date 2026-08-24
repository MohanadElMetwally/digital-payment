package com.example.digital_payment.payment.application.usecase;

import java.util.List;
import java.util.UUID;
import com.example.digital_payment.payment.application.port.in.LoadPaymentSourcesUseCase;
import com.example.digital_payment.payment.application.port.out.LoadPaymentSourcesPort;
import com.example.digital_payment.payment.domain.model.entities.PaymentSources;

public class LoadPaymentSourcesService implements LoadPaymentSourcesUseCase {
    private final LoadPaymentSourcesPort loadPaymentSourcesPort;

    public LoadPaymentSourcesService(LoadPaymentSourcesPort loadPaymentSourcesPort) {
        this.loadPaymentSourcesPort = loadPaymentSourcesPort;
    }

    @Override
    public List<PaymentSources> load(UUID transactionId) {
        return loadPaymentSourcesPort.findByTransactionId(transactionId);
    }

}
