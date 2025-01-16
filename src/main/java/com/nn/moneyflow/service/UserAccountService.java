package com.nn.moneyflow.service;

import com.nn.moneyflow.exception.UserAccountNotFoundException;
import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.repository.UserAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class UserAccountService {
    private UserAccountRepository userAccountRepository;

    public UserAccount createAccount(String firstName, String lastName, BigDecimal initialPlnBalance) {

        UserAccount account = UserAccount.builder()
                .firstName(firstName)
                .lastName(lastName)
                .plnBalance(initialPlnBalance)
                .usdBalance(BigDecimal.ZERO)
                .build();
        return userAccountRepository.save(account);
    }

    public UserAccount getAccount(Long userId) {
        return userAccountRepository.findById(userId).orElseThrow(() ->
                new UserAccountNotFoundException("Account not found for userId: " + userId));
    }

    public List<UserAccount> getAccounts() {
        return userAccountRepository.findAll();
    }

}
