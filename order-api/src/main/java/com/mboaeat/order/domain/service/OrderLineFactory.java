package com.mboaeat.order.domain.service;

import com.mboaeat.order.controller.OrderLineRequest;
import com.mboaeat.order.controller.menu.MenuConverter;
import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.OrderLine;
import com.mboaeat.order.domain.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderLineFactory {

    private final MenuService menuService;

    public OrderLineFactory(MenuService menuService) {
        this.menuService = menuService;
    }

    public OrderLine create(OrderLineRequest orderLineRequest){
        Menu menu = menuService.getMenu(orderLineRequest.getMenuReference());
        return OrderLine
                .builder()
                .menu(menu)
                .quantity(orderLineRequest.getQuantity())
                .menuPriceOption(MenuConverter.modelToMenuPriceOption(orderLineRequest.getPrice()))
                .build();
    }

    public List<OrderLine> create(List<OrderLineRequest> orderLineRequests){
        return orderLineRequests.stream().map(this::create).collect(Collectors.toList());
    }

}
