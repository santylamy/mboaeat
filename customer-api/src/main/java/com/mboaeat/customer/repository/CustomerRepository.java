package com.mboaeat.customer.repository;

import com.mboaeat.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Optional<Customer> findCustomerByUser(Long user);
}
