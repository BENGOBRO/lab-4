package ru.bengo.animaltracking.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.model.Account;
import ru.bengo.animaltracking.service.AccountService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    // @PostMapping("/registration")

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable(value = "accountId") @NotBlank @Min(1) Integer id) {
        Optional<Account> foundAccount = accountService.findById(id);

        if (foundAccount.isPresent()) {
            return ResponseEntity.ok(foundAccount.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAccounts(
            @RequestParam(required = false) Map<String, String> firstLastNamesAndEmailMap,
            @RequestParam(defaultValue = "0") @Min(0) Integer from,
            @RequestParam(defaultValue = "10") @Min(1) Integer size) {

        String firstName = firstLastNamesAndEmailMap.get("firstName");
        String lastName = firstLastNamesAndEmailMap.get("lastName");
        String email = firstLastNamesAndEmailMap.get("email");

        Optional<List<Account>> foundAccounts = accountService.search(firstName, lastName, email, from, size);

        if (foundAccounts.isPresent()) {
            return ResponseEntity.ok(foundAccounts.get());
        }

        return ResponseEntity.notFound().build();
    }
}
