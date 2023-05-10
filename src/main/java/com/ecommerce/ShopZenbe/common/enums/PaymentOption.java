package com.ecommerce.ShopZenbe.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentOption {
    CARD,
    CASH,
    PAYPAL,
    STRIPE;

    @JsonCreator
    public static PaymentOption fromString(String value) {
        return PaymentOption.valueOf(value.toUpperCase());
    }
}
