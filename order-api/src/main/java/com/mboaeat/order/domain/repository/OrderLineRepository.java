package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.OrderLine;
import com.mboaeat.order.domain.OrderLineId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, OrderLineId> {
}
