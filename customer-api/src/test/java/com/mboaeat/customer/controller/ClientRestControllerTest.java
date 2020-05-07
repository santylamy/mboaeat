package com.mboaeat.customer.controller;

import com.mboaeat.common.AbstractRestControllerTest;
import com.mboaeat.common.advice.RestResponseExceptionHandler;
import com.mboaeat.common.dto.Client;
import com.mboaeat.common.dto.ShippingAddressInfo;
import com.mboaeat.common.dto.StreetInfo;
import com.mboaeat.common.dto.User;
import com.mboaeat.customer.client.AccountClient;
import com.mboaeat.customer.hateoas.ClientModel;
import com.mboaeat.customer.hateoas.assembler.ClientModelAssembler;
import com.mboaeat.customer.service.CustomerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientRestController.class)
@ContextConfiguration(classes = {ClientRestController.class, RestResponseExceptionHandler.class, ClientModelAssembler.class})
class ClientRestControllerTest extends AbstractRestControllerTest {

    @MockBean
    CustomerService customerService;

    @MockBean
    AccountClient accountClient;

    Client client, clientCreated;

    User user;

    @BeforeAll
    public void init() {
        client = Client
                .builder()
                .address(
                        ShippingAddressInfo
                                .builder()
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
        user = User.builder().id("1").build();

        clientCreated = Client
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
    }

    @SneakyThrows
    @Test
    void newClient() {
        when(customerService.createCustomer(user, client)).thenReturn(clientCreated);
        when(accountClient.findUserById(1L)).thenReturn(user);
        mockMvc.perform(
                post("/api/v1.0/clients/withUser/" + 1 )
                        .content(objectMapper.writeValueAsString(client))
                        .contentType("application/json")
        ).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getClient() {
        when(customerService.getClientById(clientCreated.asLongId())).thenReturn(clientCreated);
        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1.0/clients/" + clientCreated.asLongId())
                        .contentType("application/json")
        ).andExpect(status().isOk()).andReturn();
        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ClientModel clientModel = objectMapper.readValue(expectedResponseBody,ClientModel.class);
        assertThat(clientModel).isNotNull();
        assertThat(clientModel.getContent().getId()).isEqualTo(clientCreated.getId());
    }

    @SneakyThrows
    @Test
    void getClientByUser() {
        when(customerService.getClientByUser(user.asLongId())).thenReturn(clientCreated);
        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1.0/clients/user/" + user.asLongId())
                        .contentType("application/json")
        ).andExpect(status().isOk()).andReturn();
        String expectedResponseBody = mvcResult.getResponse().getContentAsString();
        ClientModel clientModel = objectMapper.readValue(expectedResponseBody,ClientModel.class);
        assertThat(clientModel).isNotNull();
        assertThat(clientModel.getContent().getId()).isEqualTo(clientCreated.getId());
    }

    @SneakyThrows
    @Test
    void hasAddress_then_return_True() {
        when(customerService.hasAddress(user.asLongId())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1.0/clients/check/address")
                        .contentType("application/json")
                        .queryParam("userId", user.getId())
        ).andExpect(status().isOk()).andReturn();
        String expectedResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(expectedResponseBody).isNotNull();
        assertThat(Boolean.valueOf(expectedResponseBody)).isTrue();
    }


    @SneakyThrows
    @Test
    void hasAddress_then_return_False() {
        when(customerService.hasAddress(user.asLongId())).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(
                get("/api/v1.0/clients/check/address")
                        .contentType("application/json")
                        .queryParam("userId", user.getId())
                        .queryParam("negation", "true")
        ).andExpect(status().isOk()).andReturn();
        String expectedResponseBody = mvcResult.getResponse().getContentAsString();

        assertThat(expectedResponseBody).isNotNull();
        assertThat(Boolean.valueOf(expectedResponseBody)).isFalse();
    }
}