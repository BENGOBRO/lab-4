package ru.bengo.animaltracking.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.bengo.animaltracking.entity.Account;
import ru.bengo.animaltracking.service.AccountService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    @WithMockUser(value = "test")
    public void whenGetAccount_shouldReturn200() throws Exception {
        int id = 1;

        when(accountService.get(id)).thenReturn(
                new Account(id, "firstName", "lastName", "test@test.com", "password")
        );

        mockMvc.perform(get("/accounts/{accountId}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", Matchers.is(1)))
                .andExpect(jsonPath("firstName", Matchers.is("firstName")))
                .andExpect(jsonPath("lastName", Matchers.is("lastName")))
                .andExpect(jsonPath("email", Matchers.is("test@test.com")));
    }

    @Test
    @WithMockUser(value = "test")
    public void whenUpdateAccount_shouldReturn200() {

    }

    @Test
    @WithMockUser(value = "test")
    public void whenDeleteAccount_shouldReturn200() {

    }

    @Test
    @WithMockUser(value = "test")
    public void whenSearchAccount_shouldReturn200() {

    }
}
