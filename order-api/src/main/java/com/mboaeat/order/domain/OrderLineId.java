package com.mboaeat.order.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderLineId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    @EqualsAndHashCode.Exclude
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;
}
