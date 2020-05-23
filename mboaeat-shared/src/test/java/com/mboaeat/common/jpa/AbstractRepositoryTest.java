package com.mboaeat.common.jpa;

import com.mboaeat.common.AbstractTest;
import com.mboaeat.common.TestApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.show_sql=true",
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.cloud.consul.discovery.enabled=false",
        "spring.cloud.consul.config.enabled=false",
        "spring.cloud.consul.port=8501",
        "springdoc.api-docs.enabled=false",
        "spring.cloud.consul.discovery.instance-id=${spring.application.name}",
        "spring.datasource.url=jdbc:postgresql://${embedded.postgresql.host}:${embedded.postgresql.port}/${embedded.postgresql.schema}",
        "spring.datasource.username=${embedded.postgresql.user}",
        "spring.datasource.password=${embedded.postgresql.password}",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL10Dialect",
        "embedded.postgresql.enabled=true"
})
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EnableJpaRepositories(basePackages = "com.mboaeat")
@EntityScan(basePackages = "com.mboaeat")
@EnableJpaAuditing
public abstract class AbstractRepositoryTest extends AbstractTest {

   // @Configuration
    static class TestConfiguration {

        @Value("${spring.datasource.url}")
        private String jdbcUrl;
        @Value("${spring.datasource.username}")
        private String user;
        @Value("${spring.datasource.password}")
        private String password;

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUrl(jdbcUrl);
            ds.setUsername(user);
            ds.setPassword(password);
            return ds;
        }
    }
}
