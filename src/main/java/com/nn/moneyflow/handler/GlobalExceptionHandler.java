package com.nn.moneyflow.handler;

import com.nn.moneyflow.exception.InsufficientBalanceException;
import com.nn.moneyflow.exception.UserAccountNotFoundException;
import com.nn.moneyflow.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return ResponseBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseEntity<Object> handleUserAccountNotFound(UserAccountNotFoundException ex) {
        return ResponseBuilder.build(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getPath());
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleUserAccountNotFound(InsufficientBalanceException ex) {
        return ResponseBuilder.build(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getPath());
    }
}
