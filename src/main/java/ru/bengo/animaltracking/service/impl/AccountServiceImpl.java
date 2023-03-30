package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.exception.UserAlreadyExistException;
import ru.bengo.animaltracking.model.Account;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.model.User;
import ru.bengo.animaltracking.repository.AccountRepository;
import ru.bengo.animaltracking.service.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<Account> findById(@NotNull @Positive Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<List<Account>> search(String firstName, String lastName, String email,
                                           @Min(0) Integer from, @Min(1) Integer size) {
        return Optional.empty();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account register(@Valid Account account) throws UserAlreadyExistException{
        String email = account.getEmail();

        if (isEmailExist(email)) {
            throw new UserAlreadyExistException(Message.ACCOUNT_EXIST.getInfo());
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Account update(Account account) throws UserAlreadyExistException {
        String email = account.getEmail();

        if (isEmailExist(email)) {
            throw new UserAlreadyExistException(Message.ACCOUNT_EXIST.getInfo());
        }

        return accountRepository.save(account);
    }

    private boolean isEmailExist(String email) {

        Optional<Account> foundAccount = accountRepository.findAccountByEmail(email);
        return foundAccount.isPresent();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> foundAccount = accountRepository.findAccountByEmail(username);

        if (foundAccount.isPresent()) {
            return new User(foundAccount.get());
        }

        throw new UsernameNotFoundException(username);
    }
}
