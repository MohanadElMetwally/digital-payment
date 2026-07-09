package com.example.digital_payment.shared.application.port.in;

import java.util.Optional;
import java.util.UUID;

import com.example.digital_payment.shared.dto.BillInfo;

public interface FindPayableBillUseCase {
    Optional<BillInfo> fetchBillInfo(UUID id);
}
