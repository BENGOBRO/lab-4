package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
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
import ru.bengo.animaltracking.entity.Account;
import ru.bengo.animaltracking.exception.AccountNotFoundException;
import ru.bengo.animaltracking.exception.NoAccessException;
import ru.bengo.animaltracking.exception.UserAlreadyExistException;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.model.User;
import ru.bengo.animaltracking.repository.AccountRepository;
import ru.bengo.animaltracking.service.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder passwordEncoder;

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
    public Account get(@NotNull @Positive Integer id) throws AccountNotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(Message.ACCOUNT_NOT_FOUND_GET.getInfo()));
    }


    @Override
    public Account update(@Valid AccountDto accountDto,@NotNull @Positive Integer id) throws UserAlreadyExistException, NoAccessException, AccountNotFoundException {
        String email = accountDto.email();
        accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(Message.ACCOUNT_NOT_FOUND_METHOD.getInfo()));

        if (!isUserUpdatingTheirAccount(id)) {
            throw new NoAccessException(Message.NO_ACCESS.getInfo());
        }
        if (!isEmailExist(email, id)) {
            throw new UserAlreadyExistException(Message.ACCOUNT_EXIST.getInfo());
        }

        Account account = convertToEntity(accountDto);
        account.setId(id);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public void delete(@NotNull @Positive Integer id) throws NoAccessException, AccountNotFoundException {
        accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(Message.ACCOUNT_NOT_FOUND_METHOD.getInfo()));

        if (!isUserUpdatingTheirAccount(id)) {
            throw new NoAccessException(Message.NO_ACCESS.getInfo());
        }

        accountRepository.deleteById(id);
    }

    @Override
    public List<Account> search(String firstName, String lastName, String email,
                                @Min(0) Integer from, @Min(1) Integer size) {
        PageRequest pageRequest = PageRequest.ofSize(size + from);

        List<Account> accounts =
                accountRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderById(firstName, lastName, email, pageRequest);

        return accounts.stream().skip(from).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> foundAccount = accountRepository.findAccountByEmail(username);

        if (foundAccount.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        return new User(foundAccount.get());
    }

    private boolean isEmailExist(String email) {
        Optional<Account> foundAccount = accountRepository.findAccountByEmail(email);
        return foundAccount.isPresent();
    }

    private boolean isEmailExist(String email, Integer id) {
        Optional<Account> foundOwnerAccount = accountRepository.findById(id);
        Optional<Account> foundAccount = accountRepository.findAccountByEmail(email);

        if (foundAccount.isPresent() && foundOwnerAccount.isPresent()) {
            return foundAccount.get() == foundOwnerAccount.get();
        }

        return true;
    }

    private boolean isUserUpdatingTheirAccount(Integer id) {
        Optional<Account> foundAccount = accountRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (foundAccount.isPresent()) {
            return foundAccount.get().getEmail().equals(auth.getName());
        }

        return false;
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
