package com.example.digital_payment.settlement.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.digital_payment.settlement.domain.enums.FailedSettlementResolutionStatus;
import com.example.digital_payment.settlement.domain.snapshots.FailedSettlementSnapshot;
import com.example.digital_payment.settlement.domain.valueobjects.FailedSettlementCreationData;

public class FailedSettlements {
    private UUID id;
    private UUID settlementId;
    private UUID billId;
    private UUID userId;
    private String customerNumber;
    private BigDecimal amount;
    private String currency;
    private String failureReason;
    private int attemptCount;
    private FailedSettlementResolutionStatus resolutionStatus;
    private String resolutionNotes;
    private String resolvedBy;
    private LocalDateTime resolvedAt;
    private LocalDateTime createdAt;

    public FailedSettlements() {

    }

    public static FailedSettlements create(FailedSettlementCreationData creationData) {
        FailedSettlements failedSettlement = new FailedSettlements();
        failedSettlement.id = UUID.randomUUID();
        failedSettlement.settlementId = creationData.settlementId();
        failedSettlement.billId = creationData.billId();
        failedSettlement.userId = creationData.userId();
        failedSettlement.amount = creationData.amount();
        failedSettlement.currency = creationData.currency().toUpperCase().trim();
        failedSettlement.failureReason = creationData.failureReason();
        failedSettlement.attemptCount = creationData.attemptCount();
        failedSettlement.resolutionStatus = FailedSettlementResolutionStatus.OPEN;
        failedSettlement.resolutionNotes = null;
        failedSettlement.resolvedBy = null;
        failedSettlement.resolvedAt = null;
        failedSettlement.createdAt = LocalDateTime.now();
        return failedSettlement;
    }

    public static FailedSettlements reconstitute(FailedSettlementSnapshot snapshot) {
        FailedSettlements failedSettlement = new FailedSettlements();
        failedSettlement.id = snapshot.id();
        failedSettlement.settlementId = snapshot.settlementId();
        failedSettlement.billId = snapshot.billId();
        failedSettlement.userId = snapshot.userId();
        failedSettlement.amount = snapshot.amount();
        failedSettlement.currency = snapshot.currency();
        failedSettlement.failureReason = snapshot.failureReason();
        failedSettlement.attemptCount = snapshot.attemptCount();
        failedSettlement.resolutionStatus = snapshot.resolutionStatus();
        failedSettlement.resolutionNotes = snapshot.resolutionNotes();
        failedSettlement.resolvedBy = snapshot.resolvedBy();
        failedSettlement.resolvedAt = snapshot.resolvedAt();
        failedSettlement.createdAt = snapshot.createdAt();
        return failedSettlement;
    }

    public void resolve(String notes, String resolvedBy) {
        if (this.resolutionStatus != FailedSettlementResolutionStatus.OPEN) {
            return;
        }

        this.resolutionStatus = FailedSettlementResolutionStatus.RESOLVED;
        this.resolutionNotes = notes;
        this.resolvedBy = resolvedBy;
        this.resolvedAt = LocalDateTime.now();
    }

    public void writeOff(String notes, String resolvedBy) {
        if (this.resolutionStatus != FailedSettlementResolutionStatus.OPEN) {
            return;
        }

        this.resolutionStatus = FailedSettlementResolutionStatus.WRITTEN_OFF;
        this.resolutionNotes = notes;
        this.resolvedBy = resolvedBy;
        this.resolvedAt = LocalDateTime.now();
    }

    public void retry(String notes, String resolvedBy) {
        if (this.resolutionStatus != FailedSettlementResolutionStatus.OPEN) {
            return;
        }

        this.resolutionStatus = FailedSettlementResolutionStatus.RETRIED;
        this.resolutionNotes = notes;
        this.resolvedBy = resolvedBy;
        this.resolvedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getSettlementId() {
        return settlementId;
    }

    public UUID getBillId() {
        return billId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public FailedSettlementResolutionStatus getResolutionStatus() {
        return resolutionStatus;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
