package com.example.digital_payment.billing.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.shared.dto.BillInfo;

public interface FindPayableBillPort {
    Optional<BillInfo> findPayableById(UUID id);
}
