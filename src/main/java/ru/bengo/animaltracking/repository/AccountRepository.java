package ru.bengo.animaltracking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bengo.animaltracking.model.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    Optional<Account> findAccountByEmail(String email);
    Long deleteAccountById(Integer id);

    Page<Account> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderById(
            String firstName, String lastName, String email, Pageable pageable);
}
