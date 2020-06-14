package com.mboaeat.order.controller.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@Tag(name = "Menu registration data")
public class MenuModel {

    @Schema(description = "The menu reference")
    private Long id;

    @NotNull
    @Schema(description = "The menu name", required = true)
    private MenuName name;

    @Schema(description = "The menu nutritional elements")
    private MenuNutritional nutritional;

    @Schema(description = "The menu preparation")
    private MenuPreparationTip preparationTip;

    @Schema(description = "The menu description")
    private MenuDescription description;

    @NotNull
    @Schema(description = "The menu  ingredient elements", required = true)
    private MenuIngredient ingredient;
    @NotNull
    private MenuCost price;

    @Schema(description = "The menu status")
    private MenuOnSale onSale;

    @NotNull
    @Schema(description = "The menu price by portion", required = true)
    private List<MenuCostOption> priceOption;

    @NotBlank
    @Schema(description = "The menu category")
    private String category;

}
