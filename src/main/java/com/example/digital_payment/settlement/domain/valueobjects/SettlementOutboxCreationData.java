package com.example.digital_payment.settlement.domain.valueobjects;

import java.util.UUID;

public record SettlementOutboxCreationData(UUID settlementId) {
}