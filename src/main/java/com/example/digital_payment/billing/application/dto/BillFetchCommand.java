package com.example.digital_payment.billing.application.dto;

import java.util.UUID;

public record BillFetchCommand(UUID billerId, UUID userId, String externalCustomerNumber) {

}
