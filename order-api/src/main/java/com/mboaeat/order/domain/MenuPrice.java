package com.mboaeat.order.domain;

import com.mboaeat.common.Periodical;
import com.mboaeat.common.PeriodicalElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;

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
    private PeriodByDay period = PeriodByDay.periodByDayStartingToday();

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "MENU_PRICE")
    )
    private Amount amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;


    @Override
    public PeriodicalElement<PeriodByDay> copy(PeriodByDay period) {
        return MenuPrice
                .builder()
                .id(id)
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
    public PeriodByDay getPeriod() {
        return period;
    }

    @Override
    public int compareTo(Periodical<PeriodByDay> o) {
        return period.compareTo(o.getPeriod());
    }
}
