package com.mboaeat.order.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mboaeat.order.controller.menu.MenuCostOption;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Tag(name = "Order item request data")
public class OrderLineRequest {
    @NotNull
    @JsonProperty("product")
    @Schema(description = "The order menu reference")
    private Long menuReference;
    @NotNull
    @Schema(description = "The order item price")
    private MenuCostOption price;
    @NotNull
    @Schema(description = "The order item quantity")
    private Integer quantity;
}
