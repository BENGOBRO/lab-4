package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.api.dto.AccountDto;
import ru.bengo.animaltracking.store.entity.Account;

import java.util.List;

public interface AccountService {
    Account get(@NotNull @Positive Integer id);
    List<Account> search(String firstName, String lastName, String email,
                         @Min(0) Integer from, @Min(1) Integer size);

    Account register(@Valid AccountDto accountDto);

    Account update(@Valid AccountDto accountDto, @NotNull @Positive Integer id);

    void delete(@NotNull @Positive Integer id);
}
