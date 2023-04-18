package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AccountDto;
import ru.bengo.animaltracking.exception.NoAccessException;
import ru.bengo.animaltracking.exception.UserAlreadyExistException;
import ru.bengo.animaltracking.model.Account;
import ru.bengo.animaltracking.service.AccountService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountId") Integer id) {
        Optional<Account> foundAccount = accountService.findById(id);

        return foundAccount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

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

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDto accountDto,
                                               @PathVariable("accountId") Integer id) throws UserAlreadyExistException, NoAccessException {
        Account updatedAccount = accountService.update(accountDto, id);

        if (updatedAccount != null) {
            return ResponseEntity.ok(updatedAccount);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("accountId") Integer id) throws NoAccessException {
        Long numOfDeletedEntities = accountService.delete(id);

        if (numOfDeletedEntities == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}
