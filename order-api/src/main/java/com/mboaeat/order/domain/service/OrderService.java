package com.mboaeat.order.domain.service;

import com.mboaeat.order.controller.OrderRequest;
import com.mboaeat.order.domain.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order createOrder(OrderRequest orderRequest);

    List<Order> searchOrderByCustomer(Long customer);
}
