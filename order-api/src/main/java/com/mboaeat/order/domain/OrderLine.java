package com.mboaeat.order.domain;

import com.mboaeat.common.exception.MboaEatException;
import com.mboaeat.domain.Constants;
import com.mboaeat.order.domain.menu.CompoungMenu;
import com.mboaeat.order.domain.menu.MenuPriceOption;
import com.mboaeat.order.domain.menu.NonStructuredMenu;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "ORDERLINES")
public class OrderLine implements Serializable {

    @Embedded
    private Amount price;

    @EmbeddedId
    @ToString.Exclude
    private OrderLineId id;

    @Column(name = "UNITS")
    private Integer quantity;

    @Transient
    @Singular
    @ToString.Exclude
    @EqualsAndHashCode.Include
    private List<OrderLineRequest> orderLineRequests;

    @Builder
    private OrderLine(Menu menu, Order order, Integer quantity, MenuPriceOption menuPriceOption, List<OrderLineRequest> orderLineRequests){
        this.quantity = quantity;
        this.id = OrderLineId.builder().menu(menu).order(order).build();
        menuPrice(menuPriceOption);
        price(menuPriceOption);
    }

    private void price(MenuPriceOption menuPriceOption) {
        if (id.getMenu() instanceof NonStructuredMenu){
            this.price = menu()
                    .getProductCollection()
                    .getMenuProducts()
                    .stream()
                    .map(product -> product.getCurrentPrice().getAmount()).reduce(Amount::sum).get().multiply(quantity);
        }else {
            this.price = menuPriceOption.getAmount().multiply(quantity);
        }
    }

    private CompoungMenu compoungMenu(){
        return (CompoungMenu) this.id.getMenu();
    }

    private NonStructuredMenu menu(){
        return (NonStructuredMenu) this.id.getMenu();
    }

    private void menuPrice(MenuPriceOption menuPriceOption) {
        assertMenuPriceCompoungMenu(menuPriceOption);
    }

    private void assertMenuPriceCompoungMenu(MenuPriceOption menuPriceOption) {
        if (isCompoungMenu(menuPriceOption)){
            throw new MboaEatException(Constants.Message.MENU_MANDATORY);
        }
    }

    private boolean isCompoungMenu(MenuPriceOption menuPriceOption) {
        return ((id.getMenu() instanceof CompoungMenu) && Objects.isNull(menuPriceOption))
                || ((id.getMenu() instanceof NonStructuredMenu) && Objects.nonNull(menuPriceOption));
    }

    private boolean isCompoungMenu() {
        return (id.getMenu() instanceof CompoungMenu);
    }

    public void setOrder(Order order) {
        if (this.id.getOrder() != null) {
            this.id.getOrder().internalRemoveOrderLine(this);
        }
        this.id.setOrder(order);
        if (order != null) {
            order.internalAddOrderLine(this);
        }
    }

}
