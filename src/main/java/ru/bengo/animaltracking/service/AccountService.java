package ru.bengo.animaltracking.service;

import ru.bengo.animaltracking.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findById(Integer id);
    Optional<List<Account>> search(String firstName, String lastName, String email, Integer from, Integer size);

}
