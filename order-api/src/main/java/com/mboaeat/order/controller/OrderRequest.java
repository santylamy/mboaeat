package com.mboaeat.order.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Tag(name = "Order request data")
public class OrderRequest {

    @NotNull
    @Size(min = 1)
    @JsonProperty(value = "orderLines", required = true)
    @Schema(description = "The order's item name", required = true)
    private List<OrderLineRequest> orderLineRequests;

    @JsonProperty("promotionalCode")
    @Schema(description = "The customer promotional code")
    private String promoCode;
}
