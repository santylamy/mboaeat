package com.mboaeat.order.controller.menu;

import com.mboaeat.order.domain.menu.MenuStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MenuOnSale {
    private MenuStatus status;
    private LocalDate start;
    private LocalDate end;
}
