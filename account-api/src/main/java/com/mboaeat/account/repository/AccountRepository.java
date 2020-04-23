package com.mboaeat.account.repository;

import com.mboaeat.account.domain.Account;
import com.mboaeat.account.domain.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select ac from Account ac where ac.naturalPerson.emailAddress = :emailAddress")
    public Optional<Account> findByEmailAddress(@Param("emailAddress") EmailAddress emailAddress);

}
