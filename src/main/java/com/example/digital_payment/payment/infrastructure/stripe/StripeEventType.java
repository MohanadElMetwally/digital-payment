package com.example.digital_payment.payment.infrastructure.stripe;

public enum StripeEventType {

    PAYMENT_INTENT_SUCCEEDED("payment_intent.succeeded"),
    PAYMENT_INTENT_PAYMENT_FAILED("payment_intent.payment_failed"),
    UNKNOWN("unknown");

    private final String stripeValue;

    StripeEventType(String stripeValue) {
        this.stripeValue = stripeValue;
    }

    public String stripeValue() {
        return stripeValue;
    }

    public static StripeEventType fromStripeValue(String rawType) {
        for (StripeEventType type : values()) {
            if (type.stripeValue.equals(rawType)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}