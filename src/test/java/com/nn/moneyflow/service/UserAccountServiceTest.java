
package com.nn.moneyflow.service;

import com.nn.moneyflow.exception.UserAccountNotFoundException;
import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAccountServiceTest {

    @InjectMocks
    private UserAccountService userAccountService;

    @Mock
    private UserAccountRepository userAccountRepository;

    public UserAccountServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount_Success() {
        UserAccount account = UserAccount.builder()
                .firstName("John")
                .lastName("Doe")
                .plnBalance(BigDecimal.valueOf(1000))
                .usdBalance(BigDecimal.ZERO)
                .build();

        when(userAccountRepository.save(any(UserAccount.class))).thenReturn(account);

        UserAccount createdAccount = userAccountService.createAccount("John", "Doe", BigDecimal.valueOf(1000));

        assertNotNull(createdAccount);
        assertEquals("John", createdAccount.getFirstName());
        assertEquals("Doe", createdAccount.getLastName());
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(createdAccount.getPlnBalance()));
    }

    @Test
    void testGetAccount_NotFound() {
        when(userAccountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userAccountService.getAccount(1L));
    }
}
