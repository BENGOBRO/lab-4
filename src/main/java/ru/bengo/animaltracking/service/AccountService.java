package ru.bengo.animaltracking.service;

import ru.bengo.animaltracking.model.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> findById(Long id);
}
