package ru.bengo.animaltracking.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bengo.animaltracking.api.dto.AccountDto;
import ru.bengo.animaltracking.api.dto.mapper.AccountMapper;
import ru.bengo.animaltracking.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/registration")
    public ResponseEntity<AccountDto> registration(@RequestBody AccountDto accountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                accountMapper.toDto(accountService.register(accountDto)));
    }
}
