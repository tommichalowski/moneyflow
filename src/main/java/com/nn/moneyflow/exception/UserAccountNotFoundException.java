package com.nn.moneyflow.exception;

import lombok.Getter;

@Getter
public class UserAccountNotFoundException extends RuntimeException {

    private final String path;

    public UserAccountNotFoundException(String message) {
        super(message);
        this.path = "N/A";
    }

    public UserAccountNotFoundException(String message, String path) {
        super(message);
        this.path = path;
    }

}
