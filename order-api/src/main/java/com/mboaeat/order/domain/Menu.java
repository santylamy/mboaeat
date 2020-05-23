package com.mboaeat.order.domain;

import com.mboaeat.common.Periodical;
import com.mboaeat.common.PeriodicalElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "MENUS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MENU_TYPE")
@AttributeOverride(
        name = "id",
        column = @Column(name = "MENU_ID")
)
public abstract class Menu extends BaseEntity<Long> implements PeriodicalElement<PeriodByDay> {

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "MENU_PRICE")
    )
    protected Amount price;

    @Override
    public PeriodicalElement copy(PeriodByDay period) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PeriodByDay getPeriod() {
        return null;
    }

    @Override
    public int compareTo(Periodical<PeriodByDay> o) {
        return 0;
    }
}
