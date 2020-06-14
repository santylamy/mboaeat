package com.mboaeat.order.controller.menu;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class MenuName {
    @NotBlank
    private String menuNameFr;
    private String menuNameEn;

}
