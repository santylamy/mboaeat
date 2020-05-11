package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity<Long> {

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "TOTAL_PRICE")
    )
    private Amount totalAmount;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "TOTAL_PRICE_TVA")
    )
    private Amount totalAmountTva;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order")
    private Set<OrderLine> orderLines = new HashSet<>();

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void addOrderLine(OrderLine orderLine) {
        orderLine.setOrder(this);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLine.setOrder(null);
    }

    public void internalRemoveOrderLine(OrderLine orderLine) {
       orderLines.remove(orderLine);
    }

    public void internalAddOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
    }
}
