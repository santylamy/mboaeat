package com.mboaeat.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ORDERLINES")
public class OrderLine implements Serializable {

    @Embedded
    private Amount price;

    @EmbeddedId
    private OrderLineId id;

    @Column(name = "UNITS")
    private Integer quantity;

    @Embedded
    private ProductCollection productCollection;

    public void setOrder(Order order) {
        if (this.id.getOrder() != null) {
            this.id.getOrder().internalRemoveOrderLine(this);
        }
        this.id.setOrder(order);
        if (order != null) {
            order.internalAddOrderLine(this);
        }
    }

}
