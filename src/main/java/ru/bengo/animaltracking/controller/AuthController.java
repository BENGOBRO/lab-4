package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bengo.animaltracking.dto.AccountDto;
import ru.bengo.animaltracking.entity.Account;
import ru.bengo.animaltracking.exception.ConflictException;
import ru.bengo.animaltracking.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<Account> registration(@RequestBody AccountDto accountDto) throws ConflictException {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.register(accountDto));
    }
}
