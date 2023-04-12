package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.AccountDto;
import ru.bengo.animaltracking.exception.NoAccessException;
import ru.bengo.animaltracking.exception.UserAlreadyExistException;
import ru.bengo.animaltracking.model.Account;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.model.User;
import ru.bengo.animaltracking.repository.AccountRepository;
import ru.bengo.animaltracking.service.AccountService;

import java.util.*;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Optional<Account> findById(@NotNull @Positive Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> search(String firstName, String lastName, String email,
                                @Min(0) Integer from, @Min(1) Integer size) {
        PageRequest pageRequest = PageRequest.ofSize(size);

        Page<Account> page =
                accountRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderById(firstName, lastName, email, pageRequest);

        return page.stream().skip(from).toList();
    }

    @Override
    public Account register(@Valid AccountDto accountDto) throws UserAlreadyExistException{
        String email = accountDto.email();

        if (isEmailExist(email)) {
            throw new UserAlreadyExistException(Message.ACCOUNT_EXIST.getInfo());
        }

        Account account = convertToEntity(accountDto);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public Account update(@Valid AccountDto accountDto,@NotNull @Positive Integer id) throws UserAlreadyExistException, NoAccessException {
        String email = accountDto.email();

        if (isEmailExist(email)) {
            throw new UserAlreadyExistException(Message.ACCOUNT_EXIST.getInfo());
        }

        if (!isUserUpdatingTheirAccount(email)) {
            throw new NoAccessException(Message.NO_ACCESS.getInfo());
        }

        Account account = convertToEntity(accountDto);
        account.setId(id);
        return accountRepository.save(account);
    }

    @Override
    public Long delete(@NotNull @Positive Integer id) throws NoAccessException {
        Optional<Account> foundAccount = findById(id);
        String email = foundAccount.get().getEmail();

        if (!isUserUpdatingTheirAccount(email)) {
            throw new NoAccessException(Message.NO_ACCESS.getInfo());
        }
        return accountRepository.deleteAccountById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> foundAccount = accountRepository.findAccountByEmail(username);

        if (foundAccount.isPresent()) {
            return new User(foundAccount.get());
        }

        throw new UsernameNotFoundException(username);
    }

    private boolean isEmailExist(String email) {

        Optional<Account> foundAccount = accountRepository.findAccountByEmail(email);
        return foundAccount.isPresent();

    }

    private boolean isUserUpdatingTheirAccount(String email) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName().equals(email);

    }

    private Account convertToEntity(AccountDto accountDto) {
        return Account.builder()
                .firstName(accountDto.firstName())
                .lastName(accountDto.lastName())
                .email(accountDto.email())
                .password(accountDto.password())
                .build();
    }
}
