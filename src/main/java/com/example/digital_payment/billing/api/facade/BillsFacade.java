package com.example.digital_payment.billing.api.facade;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.digital_payment.billing.api.dto.response.BillResponse;
import com.example.digital_payment.billing.api.mapper.BillApiMapper;
import com.example.digital_payment.billing.application.dto.BillFetchCommand;
import com.example.digital_payment.billing.application.dto.BillResult;
import com.example.digital_payment.billing.application.port.in.BillFetchUseCase;
import com.example.digital_payment.shared.security.CurrentUserContext;

@Component
public class BillsFacade {
    private final BillFetchUseCase billFetchUseCase;
    private final BillApiMapper billApiMapper;
    private final CurrentUserContext currentUserContext;

    public BillsFacade(BillFetchUseCase billFetchUseCase, CurrentUserContext currentUserContext,
        BillApiMapper billApiMapper) {
        this.billFetchUseCase = billFetchUseCase;
        this.currentUserContext = currentUserContext;
        this.billApiMapper = billApiMapper;
    }

    public BillResponse fetchBillByCustomerNumber(UUID billerId, String externalCustomerNumber) {
        UUID userId = currentUserContext.getUserId();
        BillFetchCommand command = new BillFetchCommand(billerId, userId, externalCustomerNumber);
        BillResult result = billFetchUseCase.fetchBill(command);
        return billApiMapper.toResponse(result);
    }
}
