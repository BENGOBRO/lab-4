package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AccountDto;
import ru.bengo.animaltracking.entity.Account;
import ru.bengo.animaltracking.exception.BadRequestException;
import ru.bengo.animaltracking.exception.ConflictException;
import ru.bengo.animaltracking.exception.ForbiddenException;
import ru.bengo.animaltracking.exception.NotFoundException;
import ru.bengo.animaltracking.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountId") Integer id) throws NotFoundException {
        return ResponseEntity.ok(accountService.get(id));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDto accountDto,
                                               @PathVariable("accountId") Integer id) throws ForbiddenException, ConflictException {
        return ResponseEntity.ok(accountService.update(accountDto, id));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("accountId") Integer id) throws ForbiddenException, BadRequestException {
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
