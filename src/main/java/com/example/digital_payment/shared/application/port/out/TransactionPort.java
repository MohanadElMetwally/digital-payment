package com.example.digital_payment.shared.application.port.out;

import java.util.function.Supplier;

public interface TransactionPort {
    <T> T execute(Supplier<T> operation);

    void executeVoid(Runnable operation);
}
