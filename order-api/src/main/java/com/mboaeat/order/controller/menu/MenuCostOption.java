package com.mboaeat.order.controller.menu;

import com.mboaeat.common.dto.AbstractBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MenuCostOption extends AbstractBaseDTO {

    @NotNull
    @DecimalMin("1")
    private BigDecimal price;
    @NotNull
    @Min(1)
    private Integer quantity;
}
