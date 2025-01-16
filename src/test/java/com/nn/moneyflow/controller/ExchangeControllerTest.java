
package com.nn.moneyflow.controller;

import com.nn.moneyflow.model.ExchangeRequest;
import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.service.ExchangeRateService;
import com.nn.moneyflow.utils.Currency;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExchangeControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private ExchangeRateService exchangeRateService;

    public ExchangeControllerTest() {
        MockitoAnnotations.openMocks(this);
        ExchangeController exchangeController = new ExchangeController(exchangeRateService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(exchangeController).build();
    }

    @Test
    void testExchangeCurrency_Success() throws Exception {
        ExchangeRequest request = new ExchangeRequest();
        request.setSrcCurrency(Currency.PLN);
        request.setSrcAmount(BigDecimal.valueOf(500));
        request.setDestCurrency(Currency.USD);

        UserAccount account = UserAccount.builder()
                .firstName("John")
                .lastName("Doe")
                .plnBalance(BigDecimal.valueOf(500))
                .usdBalance(BigDecimal.valueOf(100))
                .build();

        when(exchangeRateService.exchangeCurrency(1L, Currency.PLN, BigDecimal.valueOf(500), Currency.USD)).thenReturn(account);

        mockMvc.perform(post("/moneyflow/exchange/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"srcCurrency\":\"PLN\",\"srcAmount\":500,\"destCurrency\":\"USD\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plnBalance").value(500))
                .andExpect(jsonPath("$.usdBalance").value(100));
    }
}
