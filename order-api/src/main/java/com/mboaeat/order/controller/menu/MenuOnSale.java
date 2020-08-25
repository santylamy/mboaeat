package com.mboaeat.order.controller.menu;

import com.mboaeat.common.dto.AbstractBaseDTO;
import com.mboaeat.order.domain.menu.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MenuOnSale extends AbstractBaseDTO {
    private MenuStatus status;

    @FutureOrPresent
    private LocalDate start;

    @FutureOrPresent
    private LocalDate end;
}
