package com.mboaeat.customer.service;

import com.mboaeat.common.dto.Client;
import com.mboaeat.customer.domain.Customer;
import com.mboaeat.customer.repository.AddressRepository;
import com.mboaeat.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import static com.mboaeat.customer.service.ServiceUtils.toCustomer;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public void createCustomer(Client client){
        Customer customer = toCustomer(client);
        customerRepository.save(customer);
    }
}
