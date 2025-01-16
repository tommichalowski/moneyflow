package com.nn.moneyflow.exception;

import lombok.Getter;

@Getter
public class InsufficientBalanceException extends RuntimeException {

    private final String path;

    public InsufficientBalanceException(String message) {
        super(message);
        this.path = "N/A";
    }

    public InsufficientBalanceException(String message, String path) {
        super(message);
        this.path = path;
    }

}
