package com.mboaeat.account.service;

import com.mboaeat.account.AbstractAccountTest;
import com.mboaeat.account.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ComponentScan(value = {"com.mboaeat.account.service"})
class AccountServiceTest extends AbstractAccountTest {

    @Test
    void createAccount() throws Exception {
        Account account = accountService.createAccount("mail@domain.com", "password");
        assertThat(account).isNotNull();
        assertThat(account.getNaturalPerson().getEmailAddress().getValue()).isEqualTo("mail@domain.com");
    }

    @Test
    void getByEmailAndPassword() throws Exception {
        Account account = accountService.createAccount("mail2@domain.com", "password");
        assertThat(account).isNotNull();
        Account accountSaved = accountService.getByEmailAndPassword("mail2@domain.com", "password");
        assertThat(accountSaved).isNotNull();
    }

    @Test
    void getByEmailAndPassword_ThrowException_Email_Not_Found() throws Exception {
        Account account = accountService.createAccount("mail3@domain.com", "password");
        assertThat(account).isNotNull();

        assertThat(
                catchThrowable(() -> accountService.getByEmailAndPassword("mail0@domain.com", "password"))
        ).hasMessage("No account found with email mail0@domain.com").isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void getByEmailAndPassword_ThrowException_Password_Not_Found() throws Exception {
        Account account = accountService.createAccount("mail4@domain.com", "password");
        assertThat(account).isNotNull();

        assertThat(
                catchThrowable(() -> accountService.getByEmailAndPassword("mail4@domain.com", "pwd"))
        ).hasMessage("No account found with email and password").isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void updateAccount() throws AccountNotFoundException {
        Account account = accountService.getAccountById(accountId1);
        assertThat(account).isNotNull();

        Account updateAccount = accountService.updateAccount(
                account.getId(), null, "Test name", "Last Name", null
                );
    }

    @Test
    void updateAccount_ThrowException_Insufficient_Password() throws AccountNotFoundException {
        Account account = accountService.createAccount("mail.pass@domain.com", "password");
        assertThat(account).isNotNull();
        Account accountSaved = accountService.getAccountById(account.getId());
        assertThat(accountSaved).isNotNull();

        Throwable throwable = catchThrowable(() -> accountService.updatePassword(
                accountSaved.getId(), "passwor", "password", "pwd"
        ));
        assertThat(throwable).hasMessage("Account password not sufficiently trusted").isInstanceOf(InsufficientPasswordException.class);
    }

    @Test
    void updateAccount_ThrowException_Insufficient_Confirm_Password() throws AccountNotFoundException {
        Account account = accountService.createAccount("mail.pass.3@domain.com", "password");
        assertThat(account).isNotNull();
        Account accountSaved = accountService.getAccountById(account.getId());
        assertThat(accountSaved).isNotNull();

        Throwable throwable = catchThrowable(() -> accountService.updatePassword(
                accountSaved.getId(), "password", "pwd", "pwd1"
        ));
        assertThat(throwable).hasMessage("Account password not sufficiently trusted").isInstanceOf(InsufficientPasswordException.class);
    }

    @Test
    void createAccount_ThrowException_Account_With_Email_Exist() throws Exception {
        accountService.createAccount("mail.check@domain.com", "passwd");
        assertThat(
                catchThrowable(() -> accountService.createAccount("mail.check@domain.com", "password"))
        ).hasMessage("Exist account with email mail.check@domain.com").isInstanceOf(AccountExistException.class);
    }

    @Test
    void getAccountById() throws AccountNotFoundException {
        Account account = accountService.getAccountById(accountId1);
        assertThat(account).isNotNull();
    }
}