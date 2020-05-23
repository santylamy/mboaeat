package com.mboaeat.order.domain;

import com.mboaeat.common.AbstractPeriodicalCollection;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class MenuCollection extends AbstractPeriodicalCollection<Menu, PeriodByDay> {

    @OneToMany
    @JoinColumn(name = "MENU_ID")
    private List<Menu> menus = List.of();

    @Override
    protected List<Menu> getPeriodicals() {
        return menus;
    }

    @Override
    public AbstractPeriodicalCollection<Menu, PeriodByDay> copy() {
        return null;
    }
}
