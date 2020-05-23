package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.ClientOrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientOrderDeliveryRepository extends JpaRepository<ClientOrderDelivery, Long> {
}
