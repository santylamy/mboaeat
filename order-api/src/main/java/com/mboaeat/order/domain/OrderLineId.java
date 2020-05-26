package com.mboaeat.order.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Builder
@Embeddable
public class OrderLineId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;
}
