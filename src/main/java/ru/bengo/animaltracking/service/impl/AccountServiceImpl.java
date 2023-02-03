package ru.bengo.animaltracking.service.impl;

import org.springframework.stereotype.Service;
import ru.bengo.animaltracking.model.Account;
import ru.bengo.animaltracking.service.AccountService;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }
}
