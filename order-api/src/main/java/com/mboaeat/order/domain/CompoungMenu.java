package com.mboaeat.order.domain;

import com.mboaeat.common.Periodical;
import com.mboaeat.common.PeriodicalElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("STRUCTURED")
public class CompoungMenu extends Menu {

    @Embedded
    private ImageCollection imageCollection;

    @Embedded
    private PeriodByDay period;

    @ElementCollection
    @CollectionTable(name = "MENU_INGREDIENTS", joinColumns = @JoinColumn(name = "MENU_ID"))
    private List<ProductIngredient> ingredients;

    @Enumerated(EnumType.STRING)
    @Column(name = "MENU_STATUS_CODE")
    private MenuStatus menuStatus = MenuStatus.Menu_Available;

    @Override
    public PeriodicalElement copy(PeriodByDay period) {
        return CompoungMenu.builder()
                .period(period)
                .ingredients(ingredients)
                .menuStatus(menuStatus)
                .price(price)
                .id(id)
                .imageCollection(imageCollection).build();
    }

    @Override
    public int compareTo(Periodical<PeriodByDay> periodical) {
        return period.compareTo(periodical.getPeriod());
    }

    @Override
    public PeriodicalElement<PeriodByDay> merge(PeriodicalElement<PeriodByDay> periodical) {
        return this.copy(period.merge(periodical.getPeriod()));
    }

    @Override
    public boolean isContentEqual(PeriodicalElement<PeriodByDay> periodical) {
        return false;
    }
}
