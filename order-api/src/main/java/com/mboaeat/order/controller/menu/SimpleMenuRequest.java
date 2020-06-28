package com.mboaeat.order.controller.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Tag(name = "Menu data")
public class SimpleMenuRequest {

    @Schema(description = "The menu reference")
    private Long id;

    @Schema(description = "The menu name")
    private String name;

    @Schema(description = "The menu nutritional")
    private String nutritional;

    @Schema(description = "The menu preparation")
    private String preparationTip;

    @Schema(description = "The menu description")
    private String description;

    @Schema(description = "The menu  ingredient elements")
    private String ingredient;

    @Schema(description = "The menu price by portion")
    private List<MenuCostOption> priceOption;

    @Schema(description = "The menu category")
    private String category;
}
