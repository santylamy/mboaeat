package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
