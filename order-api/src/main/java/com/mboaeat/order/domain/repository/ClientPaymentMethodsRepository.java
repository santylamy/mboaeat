package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.ClientPaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientPaymentMethodsRepository extends JpaRepository<ClientPaymentMethods, Long> {
}
