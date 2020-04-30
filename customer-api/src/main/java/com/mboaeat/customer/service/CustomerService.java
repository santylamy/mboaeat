package com.mboaeat.customer.service;

import com.mboaeat.common.dto.Client;
import com.mboaeat.common.dto.ErrorCodeConstants;
import com.mboaeat.common.dto.User;
import com.mboaeat.customer.domain.Customer;
import com.mboaeat.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mboaeat.customer.service.ServiceUtils.*;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Client createCustomer(User user, Client client){
        Customer customer =
        customerRepository.save(toCustomer(client, user.asLongId()));
        return toClient(customer);
    }


    /**
     * manage customer
     * @param client
     * @return Client object
     */
    @Transactional
    public Client updateCustomer(Client client){
        Customer customerToUpdate = toCustomer(client, getClientById(client.asLongId()).asLongId());

        return toClient(customerRepository.save(customerToUpdate));
    }

    /**
     * Get Client by id
     * @param id
     * @return Client object
     * @throws CustomerNotFoundException
     */
    public Client getClientById(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () ->
                        new CustomerNotFoundException("Customer not exist with id " + id, ErrorCodeConstants.CU2000)
        );

        return toClient(customer);
    }

    /**
     * Get Client by user id
     * @param userId
     * @return Client object
     * @throws CustomerNotFoundException
     */
    public Client getClientByUser(Long userId) throws CustomerNotFoundException {
        return toClient(
                customerRepository.findCustomerByUser(userId).orElseThrow(
                        () ->
                                new CustomerNotFoundException("Customer not exist with id " + userId, ErrorCodeConstants.CU2000)
                )
        );
    }
}
