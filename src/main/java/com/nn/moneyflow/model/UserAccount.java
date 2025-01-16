package com.nn.moneyflow.model;

import com.nn.moneyflow.utils.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "user_accounts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private BigDecimal plnBalance;
    private BigDecimal usdBalance;

    public BigDecimal getBalance(Currency currency) {
        return switch (currency) {
            case PLN -> plnBalance;
            case USD -> usdBalance;
            default -> throw new IllegalArgumentException("Unsopported currency " + currency);
        };
    }

    public void setBalance(Currency currency, BigDecimal amount) {
        switch (currency) {
            case PLN -> this.plnBalance = amount;
            case USD -> this.usdBalance = amount;
            default -> throw new IllegalArgumentException("Unsopported currency " + currency);
        }
    }
}
