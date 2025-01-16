package com.nn.moneyflow.utils;

public enum Currency {
    PLN,
    USD;

    public static Currency fromString(String value) {
        try {
            return Currency.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid currency value: " + value);
        }
    }
}
