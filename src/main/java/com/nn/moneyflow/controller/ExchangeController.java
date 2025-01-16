package com.nn.moneyflow.controller;

import com.nn.moneyflow.model.ExchangeRequest;
import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.service.ExchangeRateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moneyflow/exchange")
@AllArgsConstructor
public class ExchangeController {

    private ExchangeRateService exchangeRateService;

    @PostMapping("/{userId}")
    public ResponseEntity<Object> exchangeCurrency(@PathVariable Long userId, @Valid @RequestBody ExchangeRequest request) {

        UserAccount account = exchangeRateService.exchangeCurrency(userId, request.getSrcCurrency(),
                request.getSrcAmount(), request.getDestCurrency());
        return ResponseEntity.ok(account);
    }
}
