package com.mboaeat.customer.controller;

import com.mboaeat.common.advice.RestResponseExceptionHandler;
import com.mboaeat.common.dto.*;
import com.mboaeat.customer.client.AccountClient;
import com.mboaeat.customer.service.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SettingsRestController.class)
@ContextConfiguration(classes = {SettingsRestController.class, RestResponseExceptionHandler.class})
class SettingsRestControllerTest extends AbstractCustomerRestController {

    Long userId = 1L;

    @MockBean
    CustomerService customerService;

    @MockBean
    AccountClient accountClient;

    @SneakyThrows
    @Test
    void updateClient_with_No_Address_throw_Return_400() {

        Client client = Client
                .builder()
                .id("1")
                .build();

        mockMvc.perform(
                put("/api/v1.0/clients/update/user/" + userId)
                .content(objectMapper.writeValueAsString(client))
                .contentType("application/json")
        ).andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void updateClient_throw_Return_400_01() {
        when(accountClient.findUserById(userId)).thenReturn(User.builder().id(userId.toString()).build());

        Client client = Client
                .builder()
                .address(
                        BillingAddressInfo
                                .builder()
                                .build()
                )
                .build();

        mockMvc.perform(
                put("/api/v1.0/clients/update/user/" + userId)
                        .content(objectMapper.writeValueAsString(client))
                        .contentType("application/json")
        ).andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void updateClient_Return_400_02() {
        when(accountClient.findUserById(userId)).thenReturn(User.builder().id(userId.toString()).build());

        Client client = Client
                .builder()
                .address(
                        ShippingAddressInfo
                                .builder()
                                .build()
                )
                .build();

        mockMvc.perform(
                put("/api/v1.0/clients/update/user/" + userId)
                        .content(objectMapper.writeValueAsString(client))
                        .contentType("application/json")
        ).andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void updateClient_Return_204_02() {
        when(accountClient.findUserById(userId)).thenReturn(User.builder().id(userId.toString()).build());
        Client client = Client
                .builder()
                .id("1")
                .address(
                        ShippingAddressInfo
                                .builder()
                                .id("1")
                                .name("jean")
                                .lastName("dupon")
                                .city("city")
                                .country("Cameroun")
                                .streetInfo(
                                        StreetInfo
                                                .builder()
                                                .streetDescOne("Rue du cafion")
                                                .build()
                                )
                                .build()
                )
                .build();

        mockMvc.perform(
                put("/api/v1.0/clients/update/user/" + 1)
                        .content(objectMapper.writeValueAsString(client))
                        .contentType("application/json")
        ).andExpect(status().isNoContent());
    }
}