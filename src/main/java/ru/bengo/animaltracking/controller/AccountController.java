package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AccountDto;
import ru.bengo.animaltracking.exception.AccountNotFoundException;
import ru.bengo.animaltracking.exception.NoAccessException;
import ru.bengo.animaltracking.exception.UserAlreadyExistException;
import ru.bengo.animaltracking.entity.Account;
import ru.bengo.animaltracking.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountId") Integer id) throws AccountNotFoundException {
        return ResponseEntity.ok(accountService.get(id));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDto accountDto,
                                               @PathVariable("accountId") Integer id) throws UserAlreadyExistException, NoAccessException, AccountNotFoundException {
        return ResponseEntity.ok(accountService.update(accountDto, id));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("accountId") Integer id) throws NoAccessException, AccountNotFoundException {
        accountService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Account>> searchAccounts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {

        return ResponseEntity.ok(accountService.search(firstName, lastName, email, from, size));
    }
}
