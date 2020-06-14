package com.mboaeat.order.controller.menu;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class MenuIngredient {

    @NotBlank
    private String ingredientsFr;
    private String ingredientsEn;
}
