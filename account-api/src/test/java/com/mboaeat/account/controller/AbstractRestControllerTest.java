package com.mboaeat.account.controller;

import com.mboaeat.common.AbstractTest;
import com.mboaeat.common.dto.User;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
        "embedded.postgresql.enabled=false",
        "spring.cloud.consul.discovery.enabled=false",
        "spring.cloud.consul.config.enabled=false",
        "spring.cloud.consul.port=8501",
        "spring.cloud.circuit.breaker.enabled=false"
})
abstract class AbstractRestControllerTest extends AbstractTest {

    static User user;

    @BeforeAll
    static void setup(){
        user = User
                .builder()
                .id("1")
                .email("dupon.jean@gmail.com")
                .firstName("Dupon")
                .lastName("Jean")
                .middleName("dupon.jean")
                .build();
    }
}
