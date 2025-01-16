
package com.nn.moneyflow.controller;

import com.nn.moneyflow.model.CreateUserAccountRequest;
import com.nn.moneyflow.model.UserAccount;
import com.nn.moneyflow.service.UserAccountService;
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

class UserAccountControllerTest {

    private final MockMvc mockMvc;

    @Mock
    private UserAccountService userAccountService;

    public UserAccountControllerTest() {
        MockitoAnnotations.openMocks(this);
        UserAccountController userAccountController = new UserAccountController(userAccountService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userAccountController).build();
    }

    @Test
    void testCreateAccount_Success() throws Exception {
        CreateUserAccountRequest request = new CreateUserAccountRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setInitialPlnBalance(BigDecimal.valueOf(1000));

        UserAccount account = UserAccount.builder()
                .firstName("John")
                .lastName("Doe")
                .plnBalance(BigDecimal.valueOf(1000))
                .build();

        when(userAccountService.createAccount("John", "Doe", BigDecimal.valueOf(1000))).thenReturn(account);

        mockMvc.perform(post("/moneyflow/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"initialPlnBalance\":1000}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.plnBalance").value(1000));
    }
}
