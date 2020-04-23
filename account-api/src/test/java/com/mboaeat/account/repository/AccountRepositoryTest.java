package com.mboaeat.account.repository;

import com.mboaeat.account.AbstractAccountTest;
import com.mboaeat.account.domain.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountRepositoryTest extends AbstractAccountTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void whenSaved_ThenFindById(){
       Account account = accountRepository.save(
                PhysicalAccount.builder()
                        .password(Password.builder().value("password").build())
                        .naturalPerson(
                                NaturalPerson
                                        .builder()
                                        .emailAddress(
                                                EmailAddress
                                                        .builder()
                                                        .value("mail@domain.com")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build()
        );
        assertThat(accountRepository.getOne(account.getId())).isNotNull();
    }

    @Test
    public void whenSaved_ThenFindById_ThenUpdate_Password(){
        Account account = accountRepository.save(
                PhysicalAccount.builder()
                        .password(Password.builder().value("password").build())
                        .naturalPerson(
                                NaturalPerson
                                        .builder()
                                        .emailAddress(
                                                EmailAddress
                                                        .builder()
                                                        .value("mail@domain.com")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build()
        );
        Optional optional = accountRepository.findById(account.getId());
        PhysicalAccount accountSaved = (PhysicalAccount) optional.get();
        assertThat(accountSaved).isNotNull();

        accountSaved.setPassword(
                Password.builder().value("newpassword").build()
        );
        PhysicalAccount accountUpdated = accountRepository.save(accountSaved);

        assertThat(accountUpdated.getPassword().getValue()).isEqualTo("newpassword");
        assertThat(accountUpdated.getPassword().getValue()).isNotEqualTo("password");

    }
}