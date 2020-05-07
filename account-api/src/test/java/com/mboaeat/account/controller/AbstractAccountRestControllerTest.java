package com.mboaeat.account.controller;

import com.mboaeat.common.AbstractRestControllerTest;
import com.mboaeat.common.dto.User;
import org.junit.jupiter.api.BeforeAll;

abstract class AbstractAccountRestControllerTest extends AbstractRestControllerTest {

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
