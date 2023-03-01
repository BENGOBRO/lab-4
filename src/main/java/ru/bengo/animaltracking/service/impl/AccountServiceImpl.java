package ru.bengo.animaltracking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bengo.animaltracking.model.Account;
import ru.bengo.animaltracking.repository.AccountRepository;
import ru.bengo.animaltracking.service.AccountService;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<List<Account>> search(String firstName, String lastName, String email,
                                          Integer from, Integer size) {
        return Optional.empty();
    }

    public void save(Account account) {
        accountRepository.save(account);
    }
}
