package com.mboaeat.order.controller.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
