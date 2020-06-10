package com.mboaeat.order.domain;

import com.mboaeat.order.domain.menu.MenuPriceOption;
import lombok.Builder;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class OrderLineFactory {

    @Builder(builderClassName = "SmallBuilder", builderMethodName = "smallBuilder")
    private static OrderLine newOrderLine(Menu menu, Order order, Integer quantity, MenuPriceOption menuPriceOption){
        return OrderLine
                .builder()
                .menu(menu)
                .order(order)
                .menuPriceOption(menuPriceOption)
                .quantity(quantity)
                .build();
    }

    @Builder(builderClassName = "FullBuilder", builderMethodName = "fullBuilder")
    private static OrderLine newOrderLine(List<OrderLineRequest> orderLineRequests){
        return OrderLine
                .builder()
                .orderLineRequests(orderLineRequests)
                .build();
    }
}
