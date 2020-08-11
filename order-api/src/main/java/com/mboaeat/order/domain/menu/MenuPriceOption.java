package com.mboaeat.order.domain.menu;

import com.mboaeat.order.domain.Amount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.hibernate.annotations.Parent;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MenuPriceOption implements Comparable<MenuPriceOption>{

    @Column(name = "MENU_PRICE_OPTION_ID", insertable = false, updatable = false)
    private Long id;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "PRICE")
    )
    @Embedded
    @Builder.Default
    private Amount amount = Amount.one();

    @Column(name = "MENU_PRICE_OPTION_QUANTITY")
    private int quantity;

    @Parent
    private MenuPrice menuPrice;

    @Override
    public int compareTo(MenuPriceOption o) {
        MenuPriceOption otherOption = o;
        return new CompareToBuilder()
                .append(this.amount, otherOption.amount)
                .append(this.quantity, otherOption.quantity)
                .toComparison();
    }
}
