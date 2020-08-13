package com.mboaeat.order.controller.menu;

import com.mboaeat.order.domain.menu.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuOnSale {
    private MenuStatus status;

    @FutureOrPresent
    private LocalDate start;

    @FutureOrPresent
    private LocalDate end;
}
