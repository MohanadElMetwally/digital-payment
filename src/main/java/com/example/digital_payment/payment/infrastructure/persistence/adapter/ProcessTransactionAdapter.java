package com.example.digital_payment.payment.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import com.example.digital_payment.payment.application.port.out.ProcessTransactionPort;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

@Component
public class ProcessTransactionAdapter implements ProcessTransactionPort {

    @Override
    public void processTransaction(Transactions transaction) {
        throw new UnsupportedOperationException("Unimplemented method 'processTransaction'");
    }
    
}
