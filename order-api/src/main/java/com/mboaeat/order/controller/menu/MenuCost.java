package com.mboaeat.order.controller.menu;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MenuCost {
    private Double amount;
    private LocalDate start;
    private LocalDate end;
}
