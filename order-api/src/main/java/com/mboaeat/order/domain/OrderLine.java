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

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "UNITS")
    private Integer quantity;


    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.internalRemoveOrderLine(this);
        }
        this.order = order;
        if (order != null) {
            order.internalAddOrderLine(this);
        }
    }

}
