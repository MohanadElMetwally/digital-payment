package com.example.digital_payment.shared.infrastructure.persistence.adapters;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.digital_payment.shared.application.port.out.TransactionPort;

@Component
public class TransactionAdapter implements TransactionPort {

    @Override
    @Transactional
    public <T> T execute(Supplier<T> operation) {
        return operation.get();
    }

    @Override
    @Transactional
    public void executeVoid(Runnable operation) {
        operation.run();
    }
}
