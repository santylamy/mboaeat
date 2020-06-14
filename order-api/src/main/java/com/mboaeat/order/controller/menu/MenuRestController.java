package com.mboaeat.order.controller.menu;

import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.mboaeat.order.controller.menu.MenuConverter.*;

@RestController
@RequestMapping("/api/v1.0/menus")
@Tag(name = "Menu API")
@Schema(description = "Provides a list of methods that retrieve menus and their data")
@Validated
public class MenuRestController {

    private final MenuService menuService;

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "Create new menu")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newMenu(@Parameter(name = "New movie", required = true)
                            @RequestBody @Valid MenuModel menuModel){
        Menu menu = modelToMenu(menuModel);
        this.menuService.createMenu(menu);
    }
}
