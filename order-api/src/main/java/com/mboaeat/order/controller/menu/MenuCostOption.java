package com.mboaeat.order.controller.menu;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class MenuCostOption {

    private Long reference;
    @NotNull
    @DecimalMin("1")
    private BigDecimal price;
    @NotNull
    @Min(1)
    private Integer quantity;
}
