
package com.nn.moneyflow.repository;

import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.utils.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    void testSaveAndFindById_Success() {
        UserAccount account = UserAccount.builder()
                .firstName("Jane")
                .lastName("Doe")
                .plnBalance(BigDecimal.valueOf(1500))
                .usdBalance(BigDecimal.valueOf(300))
                .build();

        UserAccount savedAccount = userAccountRepository.save(account);

        Optional<UserAccount> retrievedAccount = userAccountRepository.findById(savedAccount.getUserId());
        assertTrue(retrievedAccount.isPresent());
        assertEquals("Jane", retrievedAccount.get().getFirstName());
        assertEquals(BigDecimal.valueOf(1500), retrievedAccount.get().getBalance(Currency.PLN));
    }
}
