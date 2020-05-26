package com.mboaeat.order.domain;

import com.mboaeat.common.AbstractPeriodicalCollection;

import javax.persistence.*;
import java.util.List;

import static com.mboaeat.common.CollectionsUtils.newArrayList;

@Embeddable
public class MenuPriceCollection extends AbstractPeriodicalCollection<MenuPrice, PeriodByDay> {

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuPrice> menuPrices = newArrayList();

    @Override
    protected List<MenuPrice> getPeriodicals() {
        return menuPrices;
    }

    @Override
    public AbstractPeriodicalCollection<MenuPrice, PeriodByDay> copy() {
        MenuPriceCollection menuPriceCollection = new MenuPriceCollection();
        menuPriceCollection.menuPrices = menuPrices;
        return menuPriceCollection;
    }


}
