package com.nn.moneyflow.controller.errorhandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> handleFaviconRequest() {
        return ResponseEntity.noContent().build();
    }

}
