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
import ru.bengo.animaltracking.exception.*;
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
    public Account register(@Valid AccountDto accountDto) throws ConflictException {
        String email = accountDto.email();

        if (isEmailExist(email)) {
            throw new ConflictException(Message.ACCOUNT_EXIST.getInfo());
        }

        return accountRepository.save(convertToEntity(accountDto, new Account()));
    }

    @Override
    public Account get(@NotNull @Positive Integer id) throws NotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.ACCOUNT_NOT_FOUND.getInfo()));
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
    public Account update(@Valid AccountDto accountDto,@NotNull @Positive Integer id) throws ForbiddenException, ConflictException {
        var account =accountRepository.findById(id)
                .orElseThrow(() -> new ForbiddenException(Message.ACCOUNT_NOT_FOUND.getInfo()));
        var email = accountDto.email();

        if (!isUserUpdatingTheirAccount(id)) {
            throw new ForbiddenException(Message.NO_ACCESS.getInfo());
        }
        if (!isEmailExist(email, id)) {
            throw new ConflictException(Message.ACCOUNT_EXIST.getInfo());
        }

        return accountRepository.save(convertToEntity(accountDto, account));
    }

    @Override
    public void delete(@NotNull @Positive Integer id) throws ForbiddenException, BadRequestException {
        var account =accountRepository.findById(id)
                .orElseThrow(() -> new ForbiddenException(Message.ACCOUNT_NOT_FOUND.getInfo()));

        if (!isUserUpdatingTheirAccount(id)) {
            throw new ForbiddenException(Message.NO_ACCESS.getInfo());
        }
        if (!account.getAnimals().isEmpty()) {
            throw new BadRequestException(Message.ACCOUNT_ASSOCIATION.getInfo());
        }

        accountRepository.deleteById(id);
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

    private Account convertToEntity(AccountDto accountDto, Account account) {
        account.setFirstName(accountDto.firstName());
        account.setLastName(accountDto.lastName());
        account.setEmail(accountDto.email());
        account.setPassword(passwordEncoder.encode(accountDto.password()));
        return account;
    }
}
