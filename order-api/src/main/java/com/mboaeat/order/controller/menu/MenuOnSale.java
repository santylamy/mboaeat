package com.mboaeat.order.controller.menu;

import com.mboaeat.order.domain.menu.MenuStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Data
@Builder
public class MenuOnSale {
    private MenuStatus status;

    @FutureOrPresent
    private LocalDate start;

    @FutureOrPresent
    private LocalDate end;
}
