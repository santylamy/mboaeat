package com.mboaeat.order.controller.menu;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuCostOption {
    private Double price;
    private Integer quantity;
}
