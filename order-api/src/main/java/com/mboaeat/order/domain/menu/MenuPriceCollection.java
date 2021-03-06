package com.mboaeat.order.domain.menu;

import com.mboaeat.domain.AbstractPeriodicalCollection;
import com.mboaeat.order.domain.PeriodByDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MenuPriceCollection extends AbstractPeriodicalCollection<MenuPrice, PeriodByDay> implements Serializable {

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
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

    protected void addPriceOption(List<MenuPriceOption> priceOptions){
        getCurrent().addPriceOption(priceOptions);
    }
}
