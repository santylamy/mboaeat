package com.mboaeat.order.domain.menu;

import com.mboaeat.domain.AbstractChangePeriodicalCollectionCommand;
import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.PeriodByDay;
import com.mboaeat.order.domain.menu.MenuStatus;
import com.mboaeat.order.domain.menu.MenuStatusLink;
import lombok.Builder;
import lombok.Data;

@Data
public class ChangeMenuStatusLinkCollectionCommand extends AbstractChangePeriodicalCollectionCommand<MenuStatusLink, PeriodByDay> {

    private MenuStatus menuStatus;
    private Menu menu;

    @Builder
    public ChangeMenuStatusLinkCollectionCommand(PeriodByDay period, MenuStatus menuStatus, Menu menu) {
        super(period);
        this.menuStatus = menuStatus;
        this.menu = menu;
    }

    @Override
    public MenuStatusLink change(MenuStatusLink menuStatusLink) {
        return create(menuStatusLink.getPeriod());
    }

    @Override
    public MenuStatusLink create(PeriodByDay period) {
        return MenuStatusLink.builder().menuStatus(menuStatus).menu(menu).period(period).build();
    }
}
