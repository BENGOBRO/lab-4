package ru.bengo.animaltracking.store.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bengo.animaltracking.store.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findAccountByEmail(String email);
    List<Account> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrderById(
            String firstName, String lastName, String email, Pageable pageable);
}
