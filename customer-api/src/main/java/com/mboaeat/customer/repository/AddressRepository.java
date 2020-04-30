package com.mboaeat.customer.repository;

import com.mboaeat.customer.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
