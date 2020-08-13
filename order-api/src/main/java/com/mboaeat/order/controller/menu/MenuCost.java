package com.mboaeat.order.controller.menu;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder
public class MenuCost {
    private Double amount;

    @NotNull
    @FutureOrPresent
    private LocalDate start;

    @NotNull
    @FutureOrPresent
    private LocalDate end;
}
