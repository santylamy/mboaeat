package com.mboaeat.order.controller.menu;

import com.mboaeat.common.dto.search.MenuSearchResult;
import com.mboaeat.common.exception.ResourceNotFoundException;
import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.menu.CompoungMenu;
import com.mboaeat.order.domain.menu.MenuPrice;
import com.mboaeat.order.domain.menu.MenuStatusLink;
import com.mboaeat.order.domain.service.MenuSearchService;
import com.mboaeat.order.domain.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.mboaeat.order.controller.menu.MenuConverter.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1.0/menus")
@Tag(name = "Menu API")
@Schema(description = "Provides a list of methods that retrieve menus and their data")
@Validated
public class MenuRestController {

    private final MenuService menuService;
    private final MenuSearchService menuSearchService;

    @Operation(summary = "Create new menu")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void newMenu(@Parameter(name = "New menu", required = true)
                            @RequestBody @Valid MenuRequest menuRequest){
        Menu menu = modelToMenu(menuRequest);
        if (menuRequest.getCategory() != null) {
            this.menuService.createMenu((CompoungMenu) menu, menuRequest.getCategory());
        }else {
            this.menuService.createMenu((CompoungMenu) menu);
        }
    }

    @Operation(summary = "Get menu data by id")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "No menu found") })
    @GetMapping("/menu/{menuId}")
    public MenuRequest getMenuModel(@Parameter(description = "The menu's id", required = true)
                                      @PathVariable("menuId") final Long menuId){
        Menu menu = menuService.findByMenuId(menuId).orElseThrow(() -> new ResourceNotFoundException());
        return menuToModel((CompoungMenu) menu);
    }

    @Operation(summary = "Get menu translate data by id and language code")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "No menu found") })
    @GetMapping("/menu/{menuId}/{lang}")
    public SimpleMenuRequest getMenuModel(@Parameter(description = "The menu's id", required = true)
                                  @PathVariable("menuId") final Long menuId,
                                          @PathVariable("lang") final String lang){
        Menu menu = menuService.findByMenuId(menuId).orElseThrow(() -> new ResourceNotFoundException());
        return menuToSimpleMenuModel((CompoungMenu) menu, lang);
    }

    @Operation(summary = "Update the menu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form"),
            @ApiResponse(responseCode = "404", description = "Incorrect url")
    })
    @PutMapping("/menu/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(
            @Parameter(description = "The menu id", required = true)
            @PathVariable("menuId") final Long menuId,
            @Parameter(description = "From menu change", required = true)
            @RequestBody @Valid MenuRequest menuRequest
    ){
        Menu menu = modelToMenu(menuRequest);
        menuService.updateMenu(menuId, (CompoungMenu) menu);
    }

    @Operation(summary = "Update the menu ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form"),
            @ApiResponse(responseCode = "404", description = "Incorrect url")
    })
    @PutMapping("/menu/{menuId}/ingredient")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenuIngredient(  @Parameter(description = "The menu id", required = true)
                                       @PathVariable("menuId") final Long menuId,
                                       @Parameter(description = "From menu ingredient change", required = true)
                                       @RequestBody @Valid MenuIngredient menuIngredient){
        menuService.updateIngredient(menuId, modelToIngredient(menuIngredient));
    }

    @Operation(summary = "Update the menu status period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form"),
            @ApiResponse(responseCode = "404", description = "Incorrect url")
    })
    @PutMapping("/menu/{menuId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenuStatus(
            @Parameter(description = "The menu id", required = true)
            @PathVariable("menuId") final Long menuId,
            @Parameter(description = "From menu change", required = true)
            @RequestBody @Valid MenuOnSale menuOnSale
    ){
        MenuStatusLink menuStatusLink = modelToMenuStatusLink(menuOnSale);
        menuService.changeMenuStatusToMenu(menuId, menuStatusLink);
    }

    @Operation(summary = "Update the menu status period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form"),
            @ApiResponse(responseCode = "404", description = "Incorrect url")
    })
    @PutMapping("/menu/{menuId}/price")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenuPrice(
            @Parameter(description = "The menu id", required = true)
            @PathVariable("menuId") final Long menuId,
            @Parameter(description = "From menu change", required = true)
            @RequestBody @Valid MenuCostExtended costRequest
    ){
        MenuPrice menuPrice = modelToMenuPrice(costRequest);
        menuService.changeMenuPriceToMenu(menuId, menuPrice);
    }

    @Operation(summary = "Find menus")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<MenuSearchResult> findMenus(
            @Parameter(description = "The locale")
            @RequestParam(value = "lang", required = true) final String language,
            @Parameter(description = "The name of the menu")
            @RequestParam(value = "name", required = false) final String name,
            @Parameter(description = "The description of the menu")
            @RequestParam(value = "description", required = false) final String description,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PageableDefault(sort = {"name"}, direction = Sort.Direction.DESC) final Pageable pageable
    ){
        return menuSearchService.findMenus(pageable, language, name, description);
    }

    @Operation(summary = "Get all menus")
    @GetMapping("/{lang}/")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuSearchResult> getAllMenus(@Parameter(description = "The locale")
                                              @PathVariable(value = "lang") final String language){
        return menuSearchService.getAllMenus(language);
    }

}
