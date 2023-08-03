package ru.bengo.animaltracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.bengo.animaltracking.dto.AccountDto;
import ru.bengo.animaltracking.entity.Account;
import ru.bengo.animaltracking.service.AccountService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    @WithMockUser(value = "test")
    public void whenValidAccountDto_shouldReturn201() throws Exception {
        AccountDto accountDto = new AccountDto("firstName", "lastName", "test@test.com", "password");
        int id = 1;

        when(accountService.register(accountDto)).thenReturn(
                new Account(id, "firstName", "lastName", "test@test.com", "password")
        );

        mockMvc.perform(post("/registration").with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(accountDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", Matchers.is(1)))
                .andExpect(jsonPath("firstName", Matchers.is("firstName")))
                .andExpect(jsonPath("lastName", Matchers.is("lastName")))
                .andExpect(jsonPath("email", Matchers.is("test@test.com")));
    }

}
