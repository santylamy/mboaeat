package com.mboaeat.order.domain.menu;

import com.mboaeat.domain.Periodical;
import com.mboaeat.domain.PeriodicalElement;
import com.mboaeat.order.domain.Menu;
import com.mboaeat.order.domain.PeriodByDay;
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
@Table(name = "MENU_STATUS_LINK ")
public class MenuStatusLink implements PeriodicalElement<PeriodByDay> {

    @Id
    @Column(name = "MENU_STATUS_LINK_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorMenuStatusLink")
    @SequenceGenerator(name = "idGeneratorMenuStatusLink", sequenceName = "SEQ_MENU_STATUS_LINK", allocationSize = 1)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "MENU_STATUS_CODE")
    private MenuStatus menuStatus = MenuStatus.Menu_Available;

    @Override
    public PeriodicalElement<PeriodByDay> copy(PeriodByDay period) {
        return MenuStatusLink
                .builder()
                .id(id)
                .menuStatus(menuStatus)
                .period(period)
                .menu(menu)
                .build();
    }

    @Override
    public PeriodicalElement<PeriodByDay> merge(PeriodicalElement<PeriodByDay> periodical) {
        return this.copy(period.merge(periodical.getPeriod()));
    }

    @Override
    public boolean isContentEqual(PeriodicalElement<PeriodByDay> periodical) {
        MenuStatusLink other = (MenuStatusLink) periodical;
        return new EqualsBuilder().append(menuStatus, other.menuStatus).append(menu, other.menu).isEquals();
    }

    @Override
    public PeriodByDay getPeriod() {
        return period;
    }

    @Override
    public int compareTo(Periodical<PeriodByDay> o) {
        return period.compareTo(o.getPeriod());
    }

    public void linkMenu(CompoungMenu compoungMenu) {
        this.menu = compoungMenu;
    }
}
