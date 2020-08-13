package com.mboaeat.order.controller.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuName {
    @NotBlank
    private String menuNameFr;
    private String menuNameEn;

}
