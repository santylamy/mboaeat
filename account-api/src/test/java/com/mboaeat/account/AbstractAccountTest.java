package com.mboaeat.account;

import com.mboaeat.account.service.AccountService;
import com.mboaeat.common.jpa.AbstractServiceTest;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;


@EntityScan(basePackages = "com.mboaeat.account")
@EnableJpaRepositories(basePackages = "com.mboaeat.account")
@ContextConfiguration(classes = {AccountService.class})
public abstract class AbstractAccountTest extends AbstractServiceTest {

    @Autowired
    public AccountService accountService;

    public Long accountId1, accountId2;

    @BeforeAll
    void initDb(){
        accountId1 =  accountService.createAccount("mail.pass.1@domain.com", "password").getId();
        accountId2 =  accountService.createAccount("mail.pass.2@domain.com", "password").getId();
    }
}
