package com.example.digital_payment.shared.application.port.in;

import java.util.UUID;
import com.example.digital_payment.shared.dto.BillInfo;

public interface FindBillInfoUseCase {
    BillInfo fetchBillInfo(UUID id);
}
