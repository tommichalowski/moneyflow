package com.nn.moneyflow.model;

import com.nn.moneyflow.utils.Currency;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRequest {

    private Currency srcCurrency;

    @Positive(message = "Amount must be greater than 0")
    private BigDecimal srcAmount;

    private Currency destCurrency;
}
