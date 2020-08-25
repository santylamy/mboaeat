package com.mboaeat.order.controller.menu;

import com.mboaeat.common.dto.AbstractBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MenuIngredient extends AbstractBaseDTO {

    @NotBlank
    private String ingredientsFr;
    private String ingredientsEn;
}
