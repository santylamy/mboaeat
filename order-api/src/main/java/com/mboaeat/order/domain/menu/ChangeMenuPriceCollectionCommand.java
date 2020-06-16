package com.mboaeat.order.domain.menu;

import com.mboaeat.domain.AbstractChangePeriodicalCollectionCommand;
import com.mboaeat.order.domain.Amount;
import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.PeriodByDay;
import com.mboaeat.order.domain.menu.MenuPrice;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ChangeMenuPriceCollectionCommand extends AbstractChangePeriodicalCollectionCommand<MenuPrice, PeriodByDay> {

    private Amount amount;
    private Menu menu;
    private List<MenuPriceOption> priceOptions;

    @Builder
    public ChangeMenuPriceCollectionCommand(PeriodByDay period, Amount amount, Menu menu, List<MenuPriceOption> priceOptions) {
        super(period);
        this.amount = amount;
        this.menu = menu;
        this.priceOptions = priceOptions;
    }

    @Override
    public MenuPrice change(MenuPrice menuPrice) {
        return create(menuPrice.getPeriod());
    }

    @Override
    public MenuPrice create(PeriodByDay period) {
        return MenuPrice
                .builder()
                .amount(amount)
                .priceOptionCollection(MenuPriceOptionCollection.builder().priceOptions(priceOptions).build())
                .menu(menu)
                .period(period).build();
    }
}
