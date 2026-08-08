package com.example.digital_payment.payment.application.dto;

import com.example.digital_payment.payment.application.enums.MarkPaymentStatus;
import com.example.digital_payment.payment.domain.model.entities.Transactions;

public record MarkPaymentSucceededResult(MarkPaymentStatus status, Transactions transaction) {

}