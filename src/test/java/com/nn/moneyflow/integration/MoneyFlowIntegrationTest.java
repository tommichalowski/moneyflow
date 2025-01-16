
package com.nn.moneyflow.integration;

import com.nn.moneyflow.model.CreateUserAccountRequest;
import com.nn.moneyflow.model.ExchangeRequest;
import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.repository.UserAccountRepository;
import com.nn.moneyflow.utils.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyFlowIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @BeforeEach
    void setUp() {
        userAccountRepository.deleteAll();
    }

    @Test
    void testCreateAccountAndExchangeCurrency_Success() {
        CreateUserAccountRequest createRequest = new CreateUserAccountRequest();
        createRequest.setFirstName("Alice");
        createRequest.setLastName("Smith");
        createRequest.setInitialPlnBalance(BigDecimal.valueOf(2000));

        // Create account
        ResponseEntity<UserAccount> createResponse = restTemplate.postForEntity("/moneyflow/users", createRequest, UserAccount.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        UserAccount createdAccount = createResponse.getBody();
        assertNotNull(createdAccount);
        assertEquals(BigDecimal.valueOf(2000), createdAccount.getPlnBalance());

        // Perform currency exchange
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setSrcCurrency(Currency.PLN);
        exchangeRequest.setSrcAmount(BigDecimal.valueOf(500));
        exchangeRequest.setDestCurrency(Currency.USD);

        ResponseEntity<UserAccount> exchangeResponse = restTemplate.postForEntity(
                "/moneyflow/exchange/" + createdAccount.getUserId(), exchangeRequest, UserAccount.class);

        assertEquals(HttpStatus.OK, exchangeResponse.getStatusCode());
        UserAccount updatedAccount = exchangeResponse.getBody();
        assertNotNull(updatedAccount);
        assertEquals(BigDecimal.valueOf(1500.00).setScale(2, RoundingMode.HALF_UP), updatedAccount.getPlnBalance());
        assertTrue(updatedAccount.getUsdBalance().compareTo(BigDecimal.ZERO) > 0);
    }
}
