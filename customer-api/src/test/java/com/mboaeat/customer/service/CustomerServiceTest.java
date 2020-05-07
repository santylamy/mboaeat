package com.mboaeat.customer.service;

import com.mboaeat.common.dto.*;
import com.mboaeat.common.jpa.AbstractServiceTest;
import com.mboaeat.customer.client.AccountClient;
import com.mboaeat.customer.domain.ClientName;
import com.mboaeat.customer.domain.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Objects;

import static com.mboaeat.customer.service.ServiceUtils.toCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@ComponentScan("com.mboaeat")
@EnableJpaRepositories(basePackages = {"com.mboaeat.customer.repository"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest extends AbstractServiceTest {

    Long id = 1L;
    User user, user2;
    Client client, client2, client3Saved;

    @Autowired
    CustomerService customerService;
    @MockBean
    AccountClient accountClient;

    @BeforeEach
    void setUp() {
        user = User
                .builder()
                .id(Objects.toString(id))
                .build();
    }

    @BeforeAll
    public void init() {
        client = Client
                .builder()
                .address(
                        ShippingAddressInfo
                                .builder()
                                .type(AddressTypeInfo.SHIPPING)
                                .build()
                )
                .build();
        user2 = User
                .builder()
                .id(Objects.toString(10L))
                .build();
        client2 = Client
                .builder()
                .address(
                        ShippingAddressInfo
                                .builder()
                                .type(AddressTypeInfo.SHIPPING)
                                .build()
                ).build();
        client3Saved = customerService.createCustomer(user2, client2);
    }

    @Test
    @Order(1)
    void createCustomer() {
        client = customerService.createCustomer(user, client);
        assertThat(client).isNotNull();
        assertThat(client.getId()).isNotNull();
    }

    @Test
    @Order(2)
    void updateCustomer() {
        User newUser = User.builder().id("2").build();
        Client newClient = Client
                .builder()
                .address(
                        ShippingAddressInfo
                                .builder()
                                .type(AddressTypeInfo.SHIPPING)
                                .build()
                )
                .build();
        Client clientSaved = customerService.createCustomer(newUser, newClient);
        ClientName clientName = ClientName.builder().name("Client Name").build();
        clientSaved.setName(clientName.getName());
        Client updateClient = customerService.updateCustomer(newUser.asLongId(), clientSaved);
        assertThat(updateClient).isNotNull();
        Customer customer = toCustomer(updateClient, newClient.asLongId());
        assertThat(customer.getClientName()).isEqualToComparingFieldByField(clientName);
    }

    @Test
    @Order(3)
    void updateCustomer_throw_Exception() {
        ClientName clientName = ClientName.builder().name("Client Name").build();
        client.setName(clientName.getName());

        assertThat(
                catchThrowable(() -> customerService.updateCustomer(user.asLongId(), client))
        )
                .hasMessage("Customer not exist with id " + client.asLongId())
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @Order(4)
    void getClientById() {
        assertThat(customerService.getClientById(client3Saved.asLongId())).isNotNull();
    }

    @Test
    @Order(5)
    void getClientByUser() {
        assertThat(customerService.getClientByUser(user2.asLongId())).isNotNull();
    }

    @Test
    @Order(6)
    void hasAddress_Return_False() {
        assertThat(customerService.hasAddress(20L)).isFalse();
    }

    @Test
    @Order(7)
    void hasAddress_Return_True() {
        assertThat(customerService.hasAddress(user2.asLongId())).isTrue();
    }
}