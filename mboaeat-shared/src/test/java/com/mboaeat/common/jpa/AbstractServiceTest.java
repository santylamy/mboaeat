package com.mboaeat.common.jpa;

import com.mboaeat.common.AbstractTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@EnableJpaRepositories
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.cloud.consul.discovery.instance-id=${spring.application.name}",
        "embedded.postgresql.enabled=false",
        "spring.cloud.consul.port=8501",
        "spring.cloud.circuit.breaker.enabled=false"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractServiceTest extends AbstractTest {
}
