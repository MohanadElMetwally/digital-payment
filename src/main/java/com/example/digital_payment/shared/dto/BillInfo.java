package com.example.digital_payment.shared.dto;

import java.math.BigDecimal;

public record BillInfo(String currency, BigDecimal amount) {

}
