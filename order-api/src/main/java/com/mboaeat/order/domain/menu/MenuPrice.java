package com.mboaeat.order.domain.menu;

import com.mboaeat.domain.Periodical;
import com.mboaeat.domain.PeriodicalElement;
import com.mboaeat.order.domain.Amount;
import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.PeriodByDay;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MENU_PRICES ")
public class MenuPrice implements PeriodicalElement<PeriodByDay> {

    @Id
    @Column(name = "MENU_PRICE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorMenuPrice")
    @SequenceGenerator(name = "idGeneratorMenuPrice", sequenceName = "SEQ_MENU_PRICES", allocationSize = 1)
    private Long id;

    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "startDate", column = @Column(name = "DATE_FROM")),
                    @AttributeOverride(name = "endDate", column = @Column(name = "DATE_TO"))
            }
    )
    @Builder.Default
    private PeriodByDay period = PeriodByDay.periodByDayStartingToday();

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "MENU_PRICE")
    )
    @Builder.Default
    private Amount amount = Amount.zero();

    @Embedded
    @Builder.Default
    private MenuPriceOptionCollection priceOptionCollection = new MenuPriceOptionCollection();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    @ToString.Exclude
    private Menu menu;


    @Override
    public PeriodicalElement<PeriodByDay> copy(PeriodByDay period) {
        return MenuPrice
                .builder()
                .id(id)
                .period(period)
                .priceOptionCollection(priceOptionCollection)
                .amount(amount)
                .menu(menu)
                .build();
    }

    @Override
    public boolean isContentEqual(PeriodicalElement<PeriodByDay> periodical) {
        MenuPrice other = (MenuPrice) periodical;
        return new EqualsBuilder()
                .append(amount, other.amount)
                .append(menu, other.menu)
                .append(priceOptionCollection, other.priceOptionCollection)
                .isEquals();
    }

    @Override
    public PeriodicalElement<PeriodByDay> merge(PeriodicalElement<PeriodByDay> periodical) {
        return this.copy(period.merge(periodical.getPeriod()));
    }

    @Override
    public PeriodByDay getPeriod() {
        return period;
    }

    @Override
    public int compareTo(Periodical<PeriodByDay> o) {
        return period.compareTo(o.getPeriod());
    }

    protected void linkMenu(CompoungMenu compoungMenu) {
        this.menu = compoungMenu;
    }

    public void addPriceOption(List<MenuPriceOption> priceOptions) {
        this.priceOptionCollection.getPriceOptions().addAll(priceOptions);
    }

    public List<MenuPriceOption> priceColPriceOptions(){
        return getPriceOptionCollection().getPriceOptions();
    }
}
