package com.mboaeat.order.domain.service;

import com.mboaeat.order.controller.OrderLineFactory;
import com.mboaeat.order.controller.OrderRequest;
import com.mboaeat.order.domain.Order;
import com.mboaeat.order.domain.repository.MenuRepository;
import com.mboaeat.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderLineFactory orderLineFactory;

    public OrderServiceImpl(OrderRepository orderRepository, MenuRepository menuRepository, OrderLineFactory orderLineFactory) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderLineFactory = orderLineFactory;
    }

    @Transactional
    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order createOrder(OrderRequest orderRequest) {
        Order order = Order.builder().build();
        order.addOrderLine(orderLineFactory.create(orderRequest.getOrderLineRequests()));
        return createOrder(order);
    }

    @Override
    public List<Order> searchOrderByCustomer(Long customer) {
        return orderRepository.findByCustomer(customer);
    }


}
