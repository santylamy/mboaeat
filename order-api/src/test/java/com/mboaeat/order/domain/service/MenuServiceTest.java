package com.mboaeat.order.domain.service;

import com.mboaeat.common.jpa.AbstractRepositoryTest;
import com.mboaeat.domain.TranslatableString;
import com.mboaeat.order.domain.*;
import com.mboaeat.order.domain.menu.*;
import com.mboaeat.order.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackageClasses ={MenuService.class})
class MenuServiceTest extends AbstractRepositoryTest {

    @Autowired
    ProductService productService;
    @Autowired
    MenuService menuService;

    @Test
    public void create_Non_Structured_Menu_With_Products(){
        Product product = productService.createProduct(
                Product.builder().productName(TranslatableString.builder().french("Riz blanc").build()).build()
        );

        Product product2 = productService.createProduct(
                Product.builder().productName(TranslatableString.builder().french("Sauce tomate").build()).build()
        );

        NonStructuredMenu menu = NonStructuredMenu.builder().build();

        NonStructuredMenu menuToSaved = (NonStructuredMenu) menuService.createMenu(menu, product, product2);

        assertThat(menuToSaved.getProductCollection().getMenuProducts()).hasSize(2);
        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(Amount.one());

    }

    @Test
    public void create_Non_Structured_Menu_With_Products_Update(){

        TranslatableString name = TranslatableString.builder()
                .french("Riz sauce tomate")
                .english("Riz sauce tomate")
                .build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .name(name)
                .build();

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(Amount.one());
        assertThat(menuToSaved.getName()).isEqualTo(name);

        TranslatableString description = TranslatableString
                .builder()
                .french("Desc fr")
                .english("Desc en")
                .build();

        TranslatableString nutritional = TranslatableString
                .builder()
                .french("Matière grasse: 2g")
                .english("Matiere grasse: 2g")
                .build();

        menuService.updateMenu(menuToSaved.getId(), name, nutritional, null, description);

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();

        assertThat(menuToSaved.getDescription()).isEqualTo(description);
        assertThat(menuToSaved.getNutritional()).isEqualTo(nutritional);

    }


    @Test
    public void create_Menu_Structured_With_Products(){

        CompoungMenu menu = CompoungMenu
                .builder()
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .menuPrice(MenuPrice.builder().build())
                .build();

        Menu menuToSaved = menuService.createMenu(menu);

        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(Amount.one());

    }


    @Test
    public void create_Menu_Structured_With_Products_With_Price(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        menu.addPrice(menuPrice);

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();

        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(1);
        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(Amount.one());

    }

    @Test
    public void create_Menu_Structured_With_Products_With_Price_update_Price(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        menu.addPrice(menuPrice);

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        Amount amount = Amount.builder().value(BigDecimal.valueOf(450)).build();

        menuService.changeMenuPriceToMenu(menuToSaved.getId(), MenuPrice
                .builder()
                .amount(amount)
                .menu(menuToSaved)
                .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                .build());

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();

        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(2);
        assertThat(menuToSaved.getCurrentPrice().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));
        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(amount);

    }



    @Test
    public void create_Menu_Structured_With_Products_With_Price_update_Price_With_Status_Update(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .menuPrice(menuPrice)
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        Amount amount = Amount.builder().value(BigDecimal.valueOf(450)).build();

        menuService.changeMenuPriceToMenu(menuToSaved.getId(), MenuPrice
                .builder()
                .amount(amount)
                .menu(menuToSaved)
                .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                .build());

        menuService.changeMenuStatusToMenu(
                menuToSaved.getId(),
                MenuStatusLink
                        .builder()
                        .menuStatus(MenuStatus.Menu_NotAvailable)
                        .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                        .build()
        );

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();

        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(amount);

        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(2);
        assertThat(menuToSaved.getCurrentPrice().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));

        assertThat(menuToSaved.getMenuStatusLinkCollection().getMenuStatusLinks()).hasSize(2);
        assertThat(menuToSaved.getCurrentStatus().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));
        assertThat(menuToSaved.getMenuStatusLinkCollection().getCurrent().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getLast().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getFirst().getMenuStatus()).isEqualTo(MenuStatus.Menu_Available);


    }



    @Test
    public void create_Menu_Structured_With_Products_With_Price_update_Price_With_Status_and_Ingredients_Update(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        Ingredient ingredient1 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Tomate").english("Tomato").build()
                )
                .build();

        Ingredient ingredient2 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Riz blan").english("Riz white").build()
                )
                .build();

        Ingredient ingredient3 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Poisson").english("Fish").build()
                )
                .build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .menuPrice(menuPrice)
                .ingredients(List.of(ingredient1, ingredient2, ingredient3))
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        Amount amount = Amount.builder().value(BigDecimal.valueOf(450)).build();

        menuService.changeMenuPriceToMenu(menuToSaved.getId(), MenuPrice
                .builder()
                .amount(amount)
                .menu(menuToSaved)
                .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                .build());

        menuService.changeMenuStatusToMenu(
                menuToSaved.getId(),
                MenuStatusLink
                        .builder()
                        .menuStatus(MenuStatus.Menu_NotAvailable)
                        .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                        .build()
        );

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();

        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(amount);

        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(2);
        assertThat(menuToSaved.getCurrentPrice().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));

        assertThat(menuToSaved.getMenuStatusLinkCollection().getMenuStatusLinks()).hasSize(2);
        assertThat(menuToSaved.getCurrentStatus().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));
        assertThat(menuToSaved.getMenuStatusLinkCollection().getCurrent().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getLast().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getFirst().getMenuStatus()).isEqualTo(MenuStatus.Menu_Available);

        assertThat(menuToSaved.getIngredientCollection().getIngredients()).hasSize(3);


    }

    @Test
    public void create_Menu_Structured_With_Products_With_Price_update_Price_With_Status_and_Ingredients_Update_01(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        Ingredient ingredient1 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Tomate").english("Tomato").build()
                )
                .build();

        Ingredient ingredient2 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Riz blan").english("Riz white").build()
                )
                .build();

        Ingredient ingredient3 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Poisson").english("Fish").build()
                )
                .build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .menuPrice(menuPrice)
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        Amount amount = Amount.builder().value(BigDecimal.valueOf(450)).build();

        menuService.changeMenuPriceToMenu(menuToSaved.getId(), MenuPrice
                .builder()
                .amount(amount)
                .menu(menuToSaved)
                .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                .build());

        menuService.changeMenuStatusToMenu(
                menuToSaved.getId(),
                MenuStatusLink
                        .builder()
                        .menuStatus(MenuStatus.Menu_NotAvailable)
                        .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                        .build()
        );

        menuService.addIngredients(menuToSaved.getId(), ingredient1, ingredient2, ingredient3);

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();

        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(amount);

        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(2);
        assertThat(menuToSaved.getCurrentPrice().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));

        assertThat(menuToSaved.getMenuStatusLinkCollection().getMenuStatusLinks()).hasSize(2);
        assertThat(menuToSaved.getCurrentStatus().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));
        assertThat(menuToSaved.getMenuStatusLinkCollection().getCurrent().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getLast().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getFirst().getMenuStatus()).isEqualTo(MenuStatus.Menu_Available);

        assertThat(menuToSaved.getIngredientCollection().getIngredients()).hasSize(3);


    }

    @Test
    public void create_Menu_Structured_With_Products_With_Price_update_Price_With_Status_and_Ingredients_Update_02(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        Ingredient ingredient1 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Tomate").english("Tomato").build()
                )
                .build();

        Ingredient ingredient2 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Riz blan").english("Riz white").build()
                )
                .build();

        Ingredient ingredient3 = Ingredient
                .builder()
                .name(
                        TranslatableString.builder().french("Poisson").english("Fish").build()
                )
                .build();

        MenuPriceOption option1 = MenuPriceOption
                .builder()
                .amount(Amount.one())
                .build();

        MenuPriceOption option2 = MenuPriceOption
                .builder()
                .amount(Amount.builder().value(BigDecimal.valueOf(2)).build())
                .build();

        MenuPriceOption option3 = MenuPriceOption
                .builder()
                .amount(Amount.builder().value(BigDecimal.valueOf(3)).build())
                .build();

        MenuPriceOption option4 = MenuPriceOption
                .builder()
                .amount(Amount.builder().value(BigDecimal.valueOf(4)).build())
                .build();

        MenuPriceOption option5 = MenuPriceOption
                .builder()
                .amount(Amount.builder().value(BigDecimal.valueOf(5)).build())
                .build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .menuPrice(menuPrice)
                .priceOptions(List.of(option1, option2, option3, option4, option5))
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        Amount amount = Amount.builder().value(BigDecimal.valueOf(450)).build();

        menuService.changeMenuPriceToMenu(menuToSaved.getId(), MenuPrice
                .builder()
                .amount(amount)
                .menu(menuToSaved)
                .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                .build());

        menuService.changeMenuStatusToMenu(
                menuToSaved.getId(),
                MenuStatusLink
                        .builder()
                        .menuStatus(MenuStatus.Menu_NotAvailable)
                        .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                        .build()
        );

        menuService.addIngredients(menuToSaved.getId(), ingredient1, ingredient2, ingredient3);

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();

        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(amount);

        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(2);
        assertThat(menuToSaved.getCurrentPrice().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));

        assertThat(menuToSaved.getMenuStatusLinkCollection().getMenuStatusLinks()).hasSize(2);
        assertThat(menuToSaved.getCurrentStatus().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));
        assertThat(menuToSaved.getMenuStatusLinkCollection().getCurrent().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getLast().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getFirst().getMenuStatus()).isEqualTo(MenuStatus.Menu_Available);

        assertThat(menuToSaved.getIngredientCollection().getIngredients()).hasSize(3);

        assertThat(menuToSaved.getMenuPriceCollection().getBeforeCurrent().getPriceOptionCollection().getPriceOptions()).hasSize(5);


        Ingredient ingredientToRemove = menuToSaved.getIngredientCollection().getIngredients().iterator().next();

        menuService.removeIngredients(menuToSaved.getId(), ingredientToRemove);
        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();
        assertThat(menuToSaved.getIngredientCollection().getIngredients()).hasSize(2);


    }



}