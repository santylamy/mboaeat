package com.mboaeat.account;

import com.mboaeat.common.jpa.AbstractRepositoryTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan(basePackages = "com.mboaeat.account")
@EnableJpaRepositories(basePackages = "com.mboaeat.account")
public abstract class AbstractAccountTest extends AbstractRepositoryTest {
}
