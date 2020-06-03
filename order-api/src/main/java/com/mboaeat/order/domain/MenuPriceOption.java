package com.mboaeat.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MenuPriceOption {

    @AttributeOverride(
            name = "value",
            column = @Column(name = "MENU_PRICE_OPTION")
    )
    @Embedded
    @Builder.Default
    private Amount amount = Amount.one();
}
