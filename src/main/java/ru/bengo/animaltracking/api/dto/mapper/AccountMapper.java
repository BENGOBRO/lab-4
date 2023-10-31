package ru.bengo.animaltracking.api.dto.mapper;

import org.mapstruct.*;
import ru.bengo.animaltracking.api.dto.AccountDto;
import ru.bengo.animaltracking.store.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account account);

    Account toEntity(AccountDto accountDto);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(AccountDto accountDto, @MappingTarget Account account);

}
