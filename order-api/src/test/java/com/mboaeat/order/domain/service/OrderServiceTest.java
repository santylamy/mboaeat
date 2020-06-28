package com.mboaeat.order.domain.service;

import com.mboaeat.common.jpa.AbstractRepositoryTest;
import com.mboaeat.domain.TranslatableString;
import com.mboaeat.order.domain.*;
import com.mboaeat.order.domain.menu.*;
import com.mboaeat.order.domain.product.Product;
import com.mboaeat.order.domain.product.ProductPrice;
import com.mboaeat.order.domain.repository.MenuPriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackageClasses ={OrderService.class})
class OrderServiceTest extends AbstractRepositoryTest {

    @Autowired
    ProductService productService;
    @Autowired
    MenuService menuService;

    @Autowired
    OrderService orderService;

    @Autowired
    MenuPriceRepository menuPriceRepository;

    @Test
    public void create_Non_Structured_Menu_With_Products(){
        Product product = productService.createProduct(
                Product.builder()
                        .productPrice(ProductPrice.builder().period(PeriodByDay.periodByDayStartingToday()).amount(Amount.one()).build())
                        .productName(TranslatableString.builder().french("Riz blanc").build()).build()
        );

        Product product2 = productService.createProduct(
                Product.builder()
                        .productPrice(ProductPrice.builder().period(PeriodByDay.periodByDayStartingToday()).amount(Amount.one()).build())
                        .productName(TranslatableString.builder().french("Sauce tomate").build()).build()
        );

        NonStructuredMenu menu = NonStructuredMenu.builder().build();

        NonStructuredMenu menuToSaved = (NonStructuredMenu) menuService.createMenu(menu, product, product2);

        assertThat(menuToSaved.getProductCollection().getMenuProducts()).hasSize(2);
        assertThat(menuToSaved.getId()).isNotZero();
        assertThat(menuToSaved.getPrice()).isNotEqualTo(Amount.one());

        Order order = Order.builder().build();

        OrderLine orderLine = OrderLine.builder()
                .menu(menu)
                .order(order)
                .quantity(2).build();

        order.addOrderLine(orderLine);

        order = orderService.createOrder(order);

        assertThat(order.getId()).isNotZero();
        assertThat(order.getTotalAmount()).isEqualTo(Amount.builder().value(BigDecimal.valueOf(4)).build());
    }

    @Test
    public void create_Menu_Structured_With_Products_With_Price(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        MenuPriceOption option1 = MenuPriceOption
                .builder()
                .quantity(1)
                .amount(Amount.builder().value(BigDecimal.valueOf(400)).build())
                .build();

        MenuPriceOption option2 = MenuPriceOption
                .builder()
                .quantity(2)
                .amount(Amount.builder().value(BigDecimal.valueOf(360)).build())
                .build();

        MenuPriceOption option3 = MenuPriceOption
                .builder()
                .quantity(3)
                .amount(Amount.builder().value(BigDecimal.valueOf(330)).build())
                .build();

        MenuPriceOption option4 = MenuPriceOption
                .builder()
                .quantity(4)
                .amount(Amount.builder().value(BigDecimal.valueOf(300)).build())
                .build();

        MenuPriceOption option5 = MenuPriceOption
                .builder()
                .quantity(5)
                .amount(Amount.builder().value(BigDecimal.valueOf(260)).build())
                .build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .menuPrice(menuPrice)
                .priceOptions(List.of(option1, option2, option3, option4, option5))
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();

        MenuPriceOption priceOption = menuToSaved
                .getMenuPriceCollection()
                .getCurrent()
                .getPriceOptionCollection()
                .getPriceOptions().get(1);

        MenuPriceOption option = MenuPriceOption
                .builder()
                .id(priceOption.getId())
                .quantity(priceOption.getQuantity())
                .amount(priceOption.getAmount()).build();

        MenuPrice mp = menuPriceRepository.getByMenuPriceOption(option);

        assertThat(mp).isNotNull();


        Order order = Order.builder().build();


        OrderLine orderLine = OrderLine.builder()
                .order(order)
                .quantity(option.getQuantity())
                .menuPriceOption(option)
                .menu(menuToSaved)
                .build();

        order.addOrderLine(orderLine);

        order = orderService.createOrder(order);

        assertThat(order.getOrderLines()).hasSize(1);
        assertThat(order.getTotalAmount()).isEqualTo(Amount.builder().value(BigDecimal.valueOf(720)).build());

    }

    @Test
    public void create_Menu_Structured_With_Products_With_Price_01(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        MenuPriceOption option1 = MenuPriceOption
                .builder()
                .quantity(1)
                .amount(Amount.builder().value(BigDecimal.valueOf(400)).build())
                .build();

        MenuPriceOption option2 = MenuPriceOption
                .builder()
                .quantity(2)
                .amount(Amount.builder().value(BigDecimal.valueOf(360)).build())
                .build();

        MenuPriceOption option3 = MenuPriceOption
                .builder()
                .quantity(3)
                .amount(Amount.builder().value(BigDecimal.valueOf(330)).build())
                .build();

        MenuPriceOption option4 = MenuPriceOption
                .builder()
                .quantity(4)
                .amount(Amount.builder().value(BigDecimal.valueOf(300)).build())
                .build();

        MenuPriceOption option5 = MenuPriceOption
                .builder()
                .quantity(5)
                .amount(Amount.builder().value(BigDecimal.valueOf(260)).build())
                .build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .menuPrice(menuPrice)
                .priceOptions(List.of(option1, option2, option3, option4, option5))
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        MenuPrice menuPrice2 = MenuPrice.builder().build();

        MenuPriceOption option1MenuPrice2 = MenuPriceOption
                .builder()
                .quantity(1)
                .amount(Amount.builder().value(BigDecimal.valueOf(100)).build())
                .build();

        MenuPriceOption option2MenuPrice2 = MenuPriceOption
                .builder()
                .quantity(2)
                .amount(Amount.builder().value(BigDecimal.valueOf(70)).build())
                .build();

        CompoungMenu menu2 = CompoungMenu
                .builder()
                .menuPrice(menuPrice2)
                .priceOptions(List.of(option1MenuPrice2, option2MenuPrice2))
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz haricot").build())
                .build();

        CompoungMenu menuToSaved2 = (CompoungMenu) menuService.createMenu(menu2);

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();
        menuToSaved2 = (CompoungMenu) menuService.findByMenuId(menuToSaved2.getId()).get();

        MenuPriceOption priceOption = menuToSaved
                .getMenuPriceCollection()
                .getCurrent()
                .getPriceOptionCollection()
                .getPriceOptions().get(1);

        MenuPriceOption priceOption2 = menuToSaved2
                .getMenuPriceCollection()
                .getCurrent()
                .getPriceOptionCollection()
                .getPriceOptions().get(1);

        MenuPriceOption option_1 = MenuPriceOption
                .builder()
                .id(priceOption.getId())
                .quantity(priceOption.getQuantity())
                .amount(priceOption.getAmount()).build();

        MenuPriceOption option_2 = MenuPriceOption
                .builder()
                .id(priceOption2.getId())
                .quantity(priceOption2.getQuantity())
                .amount(priceOption2.getAmount()).build();



        Order order = Order.builder().build();


        OrderLine orderLine1 = OrderLine.builder()
                .order(order)
                .quantity(option_1.getQuantity())
                .menuPriceOption(option_1)
                .menu(menuToSaved)
                .build();
        OrderLine orderLine2 = OrderLine.builder()
                .order(order)
                .quantity(option_2.getQuantity())
                .menuPriceOption(option_2)
                .menu(menuToSaved2)
                .build();

        order.addOrderLine(orderLine1).addOrderLine(orderLine2);

        order = orderService.createOrder(order);

        assertThat(order.getOrderLines()).hasSize(2);
        assertThat(order.getTotalAmount()).isEqualTo(Amount.builder().value(BigDecimal.valueOf(860)).build());

    }

    @Test
    public void create_Menu_Structured_With_Products_With_Price_02(){

        MenuPrice menuPrice = MenuPrice.builder().build();

        MenuPriceOption option1 = MenuPriceOption
                .builder()
                .quantity(1)
                .amount(Amount.builder().value(BigDecimal.valueOf(400)).build())
                .build();

        MenuPriceOption option2 = MenuPriceOption
                .builder()
                .quantity(2)
                .amount(Amount.builder().value(BigDecimal.valueOf(360)).build())
                .build();

        MenuPriceOption option3 = MenuPriceOption
                .builder()
                .quantity(3)
                .amount(Amount.builder().value(BigDecimal.valueOf(330)).build())
                .build();

        MenuPriceOption option4 = MenuPriceOption
                .builder()
                .quantity(4)
                .amount(Amount.builder().value(BigDecimal.valueOf(300)).build())
                .build();

        MenuPriceOption option5 = MenuPriceOption
                .builder()
                .quantity(5)
                .amount(Amount.builder().value(BigDecimal.valueOf(260)).build())
                .build();

        CompoungMenu menu = CompoungMenu
                .builder()
                .menuPrice(menuPrice)
                .priceOptions(List.of(option1, option2, option3, option4, option5))
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz Sauce tomate poisson").build())
                .build();

        CompoungMenu menuToSaved = (CompoungMenu) menuService.createMenu(menu);

        MenuPrice menuPrice2 = MenuPrice.builder().build();

        MenuPriceOption option1MenuPrice2 = MenuPriceOption
                .builder()
                .quantity(1)
                .amount(Amount.builder().value(BigDecimal.valueOf(100)).build())
                .build();

        MenuPriceOption option2MenuPrice2 = MenuPriceOption
                .builder()
                .quantity(2)
                .amount(Amount.builder().value(BigDecimal.valueOf(70)).build())
                .build();

        CompoungMenu menu2 = CompoungMenu
                .builder()
                .menuPrice(menuPrice2)
                .priceOptions(List.of(option1MenuPrice2, option2MenuPrice2))
                .menuStatusLink(MenuStatusLink.builder().menuStatus(MenuStatus.Menu_Available).build())
                .name(TranslatableString.builder().french("Riz haricot").build())
                .build();

        CompoungMenu menuToSaved2 = (CompoungMenu) menuService.createMenu(menu2);

        menuToSaved = (CompoungMenu) menuService.findByMenuId(menuToSaved.getId()).get();
        menuToSaved2 = (CompoungMenu) menuService.findByMenuId(menuToSaved2.getId()).get();

        MenuPriceOption priceOption = menuToSaved
                .getMenuPriceCollection()
                .getCurrent()
                .getPriceOptionCollection()
                .getPriceOptions().get(1);

        MenuPriceOption priceOption2 = menuToSaved2
                .getMenuPriceCollection()
                .getCurrent()
                .getPriceOptionCollection()
                .getPriceOptions().get(1);

        MenuPriceOption option_1 = MenuPriceOption
                .builder()
                .id(priceOption.getId())
                .quantity(priceOption.getQuantity())
                .amount(priceOption.getAmount()).build();

        MenuPriceOption option_2 = MenuPriceOption
                .builder()
                .id(priceOption2.getId())
                .quantity(priceOption2.getQuantity())
                .amount(priceOption2.getAmount()).build();



        Order order = Order.builder().build();


        OrderLine orderLine1 = OrderLine.builder()
                .order(order)
                .quantity(option_1.getQuantity())
                .menuPriceOption(option_1)
                .menu(menuToSaved)
                .build();
        OrderLine orderLine2 = OrderLine.builder()
                .order(order)
                .quantity(option_2.getQuantity())
                .menuPriceOption(option_2)
                .menu(menuToSaved2)
                .build();

        order.addOrderLine(List.of(orderLine1, orderLine2));

        order = orderService.createOrder(order);

        assertThat(order.getOrderLines()).hasSize(2);
        assertThat(order.getTotalAmount()).isEqualTo(Amount.builder().value(BigDecimal.valueOf(860)).build());

    }



}