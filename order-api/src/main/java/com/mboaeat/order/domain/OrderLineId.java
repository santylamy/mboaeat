package com.mboaeat.order.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class OrderLineId implements Serializable {

    //@Id
    @ManyToOne
    @JoinColumn(name = "MENU_ID")
    private Menu menu;

    //@Id
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;
}
