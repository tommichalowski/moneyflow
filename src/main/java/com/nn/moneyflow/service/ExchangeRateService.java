package com.nn.moneyflow.service;

import com.nn.moneyflow.exception.InsufficientBalanceException;
import com.nn.moneyflow.exception.UserAccountNotFoundException;
import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.repository.UserAccountRepository;
import com.nn.moneyflow.utils.Currency;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class ExchangeRateService {

    private static final Integer SCALE = 4;

    private UserAccountRepository userAccountRepository;
    private NbpExchangeRateService nbpExchangeRateService;

    public UserAccount exchangeCurrency(Long userId, Currency srcCurrency, BigDecimal srcAmount, Currency destCurrency) {

        UserAccount account = userAccountRepository.findById(userId).orElse(null);
        if (account == null) {
            throw new UserAccountNotFoundException("Account not found for userId: " + userId, "/moneyflow/exchange/" + userId);
        }
        if (account.getBalance(srcCurrency).compareTo(srcAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient " + srcCurrency + " balance.");
        }

        BigDecimal rate = nbpExchangeRateService.getExchangeRate(srcCurrency.name(), destCurrency.name());
        BigDecimal exchangedTo = srcAmount.divide(rate, SCALE, RoundingMode.HALF_UP);
        account.setBalance(srcCurrency, account.getBalance(srcCurrency).subtract(srcAmount));
        account.setBalance(destCurrency, account.getBalance(destCurrency).add(exchangedTo));

        return userAccountRepository.save(account);
    }

}
