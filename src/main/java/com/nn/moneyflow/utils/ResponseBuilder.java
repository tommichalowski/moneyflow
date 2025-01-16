package com.nn.moneyflow.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

public class ResponseBuilder {

    public static ResponseEntity<Object> build(HttpStatus httpStatus, String message, String path) {
        return ResponseEntity
                .status(httpStatus)
                .body(buildResponseParams(httpStatus, message, path));
    }

    public static ResponseEntity<Object> build(HttpStatus httpStatus, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(buildResponseParams(httpStatus, message, "N/A"));
    }

    private static Map<String, Object> buildResponseParams(HttpStatus status, String message, String path) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message,
                "path", path
        );
    }
}
