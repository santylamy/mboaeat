package com.mboaeat.order.domain.service;

import com.mboaeat.common.jpa.AbstractRepositoryTest;
import com.mboaeat.order.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.Arrays;
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
                Product.builder().productName(Name.builder().nameFr("Riz blanc").build()).build()
        );

        Product product2 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Sauce tomate").build()).build()
        );

        NonStructuredMenu menu = NonStructuredMenu.builder().build();

        Menu menuToSaved = menuService.createMenu(menu, product, product2);

        assertThat(menuToSaved.getProductCollection().getMenuProducts()).hasSize(2);
        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(Amount.one());

    }


    @Test
    public void create_Menu_Structured_With_Products(){
        Product product = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Riz blanc").build()).build()
        );

        Product product2 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Sauce tomate").build()).build()
        );

        Product product3 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Poisson").build()).build()
        );

        CompoungMenu menu = CompoungMenu
                .builder()
                .name(Name.builder().nameFr("Riz Sauce tomate poisson").build())
                .menuPrice(MenuPrice.builder().build())
                .build();

        Menu menuToSaved = menuService.createMenu(menu, product, product2, product3);

        assertThat(menuToSaved.getProductCollection().getMenuProducts()).hasSize(3);
        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(Amount.one());

    }


    @Test
    public void create_Menu_Structured_With_Products_With_Price(){
        Product product = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Riz blanc").build()).build()
        );

        Product product2 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Sauce tomate").build()).build()
        );

        Product product3 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Poisson").build()).build()
        );

        MenuPrice menuPrice = MenuPrice.builder().build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .name(Name.builder().nameFr("Riz Sauce tomate poisson").build())
                .build();

        menu.addPrice(menuPrice);

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu, product, product2, product3);

        //menuService.linkMenuPriceToMenu(menuToSaved.getId(), menuPrice);

        menuToSaved = (CompoungMenu) menuService.getMenu(menuToSaved.getId()).get();

        assertThat(menuToSaved.getProductCollection().getMenuProducts()).hasSize(3);
        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(1);
        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(Amount.one());

    }

    @Test
    public void create_Menu_Structured_With_Products_With_Price_update_Price(){
        Product product = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Riz blanc").build()).build()
        );

        Product product2 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Sauce tomate").build()).build()
        );

        Product product3 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Poisson").build()).build()
        );

        MenuPrice menuPrice = MenuPrice.builder().build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .name(Name.builder().nameFr("Riz Sauce tomate poisson").build())
                .build();

        menu.addPrice(menuPrice);

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu, product, product2, product3);

        Amount amount = Amount.builder().value(BigDecimal.valueOf(450)).build();

        menuService.changeMenuPriceToMenu(menuToSaved.getId(), MenuPrice
                .builder()
                .amount(amount)
                .menu(menuToSaved)
                .period(PeriodByDay.periodByDayStartingTodayPlusMonth(3))
                .build());

        menuToSaved = (CompoungMenu) menuService.getMenu(menuToSaved.getId()).get();

        assertThat(menuToSaved.getProductCollection().getMenuProducts()).hasSize(3);
        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(2);
        assertThat(menuToSaved.getCurrentPrice().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));
        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(amount);

    }



    @Test
    public void create_Menu_Structured_With_Products_With_Price_update_Price_With_Status_Update(){
        Product product = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Riz blanc").build()).build()
        );

        Product product2 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Sauce tomate").build()).build()
        );

        Product product3 = productService.createProduct(
                Product.builder().productName(Name.builder().nameFr("Poisson").build()).build()
        );

        MenuPrice menuPrice = MenuPrice.builder().build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .menuPrice(menuPrice)
                .products(List.of(product, product2, product3))
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(Name.builder().nameFr("Riz Sauce tomate poisson").build())
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

        menuToSaved = (CompoungMenu) menuService.getMenu(menuToSaved.getId()).get();

        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(amount);

        assertThat(menuToSaved.getProductCollection().getMenuProducts()).hasSize(3);
        assertThat(menuToSaved.getMenuPriceCollection().getMenuPrices()).hasSize(2);
        assertThat(menuToSaved.getCurrentPrice().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));

        assertThat(menuToSaved.getMenuStatusLinkCollection().getMenuStatusLinks()).hasSize(2);
        assertThat(menuToSaved.getCurrentStatus().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingTodayPlusMonth(3));
        assertThat(menuToSaved.getMenuStatusLinkCollection().getCurrent().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getLast().getMenuStatus()).isEqualTo(MenuStatus.Menu_NotAvailable);
        assertThat(menuToSaved.getMenuStatusLinkCollection().getFirst().getMenuStatus()).isEqualTo(MenuStatus.Menu_Available);


    }
}