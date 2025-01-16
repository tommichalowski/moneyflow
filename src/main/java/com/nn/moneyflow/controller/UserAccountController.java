package com.nn.moneyflow.controller;

import com.nn.moneyflow.model.CreateUserAccountRequest;
import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moneyflow/users")
@AllArgsConstructor
public class UserAccountController {

    private UserAccountService userAccountService;

    @PostMapping
    public ResponseEntity<UserAccount> createAccount(@Valid @RequestBody CreateUserAccountRequest request) {

        UserAccount userAccount = userAccountService.createAccount(request.getFirstName(), request.getLastName(),
                request.getInitialPlnBalance());
        return ResponseEntity.status(HttpStatus.CREATED).body(userAccount);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getAccount(@PathVariable Long userId) {

        UserAccount account = userAccountService.getAccount(userId);
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<List<UserAccount>> getAllAccounts() {
        List<UserAccount> accounts = userAccountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

}
