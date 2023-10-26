package ru.bengo.animaltracking.api.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bengo.animaltracking.api.dto.AccountDto;
import ru.bengo.animaltracking.store.entity.Account;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final ModelMapper modelMapper;

    public AccountDto toDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

}
