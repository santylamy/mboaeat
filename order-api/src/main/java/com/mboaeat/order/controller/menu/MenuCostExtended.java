package com.mboaeat.order.controller.menu;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@SuperBuilder
public class MenuCostExtended extends MenuCost {
    @NotNull
    private List<MenuCostOption> costOptions;
}
