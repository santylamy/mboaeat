package com.mboaeat.order.domain;

import com.mboaeat.domain.Periodical;
import com.mboaeat.domain.PeriodicalElement;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MENU_PRICES ")
public class MenuPrice implements PeriodicalElement<PeriodByDay> {

    @Id
    @Column(name = "MENU_PRICE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorPMenuPrice")
    @SequenceGenerator(name = "idGeneratorMenuPrice", sequenceName = "SEQ_MENU_PRICES", allocationSize = 1)
    private Long id;

    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "startDate", column = @Column(name = "DATE_FROM"))
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
                .amount(amount)
                .menu(menu)
                .build();
    }

    @Override
    public boolean isContentEqual(PeriodicalElement<PeriodByDay> periodical) {
        MenuPrice other = (MenuPrice) periodical;
        return new EqualsBuilder().append(amount, other.amount).append(menu, other.menu).isEquals();
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
}
