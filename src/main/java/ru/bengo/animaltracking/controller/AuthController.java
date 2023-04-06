package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bengo.animaltracking.exception.UserAlreadyExistException;
import ru.bengo.animaltracking.model.Account;
import ru.bengo.animaltracking.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody Account account) throws UserAlreadyExistException {
        return ResponseEntity.ok(accountService.register(account));
    }
}
