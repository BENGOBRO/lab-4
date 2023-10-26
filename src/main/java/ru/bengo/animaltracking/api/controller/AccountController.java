package ru.bengo.animaltracking.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.api.dto.AccountDto;
import ru.bengo.animaltracking.api.dto.mapper.AccountMapper;
import ru.bengo.animaltracking.service.AccountService;
import ru.bengo.animaltracking.store.entity.Account;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("accountId") Integer id) {
        return ResponseEntity.ok(accountMapper.toDto(accountService.get(id)));
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto,
                                               @PathVariable("accountId") Integer id) {
        return ResponseEntity.ok(accountMapper.toDto(accountService.update(accountDto, id)));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable("accountId") Integer id) {
        accountService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountDto>> searchAccounts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        List<Account> accounts = accountService.search(firstName, lastName, email, from, size);
        return ResponseEntity.ok(accounts.stream().map(accountMapper::toDto).toList());
    }
}
