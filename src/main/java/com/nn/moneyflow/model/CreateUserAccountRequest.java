package com.nn.moneyflow.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateUserAccountRequest {

    @NotBlank(message = "firstName cannot be blank")
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
    private String lastName;

    @PositiveOrZero(message = "Initial balance must be at least 0")
    private BigDecimal initialPlnBalance;
}
