package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.exception.NoAccessException;
import ru.bengo.animaltracking.exception.UserAlreadyExistException;
import ru.bengo.animaltracking.model.Account;
import ru.bengo.animaltracking.service.AccountService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable("accountId") Integer id) {
        Optional<Account> foundAccount = accountService.findById(id);

        if (foundAccount.isPresent()) {
            return ResponseEntity.ok(foundAccount.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAccounts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {

        return ResponseEntity.ok(accountService.search(firstName, lastName, email, from, size));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<?> updateAccount(@RequestBody Account account,
                                               @PathVariable("accountId") Integer id) throws UserAlreadyExistException, NoAccessException {
        Account updatedAccount = accountService.update(account, id);

        if (updatedAccount != null) {
            return ResponseEntity.ok(account);
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
