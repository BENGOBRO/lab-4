package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.AccountDto;
import ru.bengo.animaltracking.exception.*;
import ru.bengo.animaltracking.entity.Account;

import java.util.List;

public interface AccountService {
    Account get(@NotNull @Positive Integer id) throws NotFoundException;
    List<Account> search(String firstName, String lastName, String email,
                         @Min(0) Integer from, @Min(1) Integer size);

    Account register(@Valid AccountDto accountDto) throws ConflictException;

    Account update(@Valid AccountDto accountDto, @NotNull @Positive Integer id) throws ForbiddenException, ConflictException;

    void delete(@NotNull @Positive Integer id) throws ForbiddenException, BadRequestException;
}
