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
@AttributeOverride(
        name = "id",
        column = @Column(name = "ORDER_ID")
)
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

    @Column(name = "DATE_ORDER_PLACED")
    private LocalDateTime datePlaced;

    @Column(name = "DATE_ORDER_PAID")
    private LocalDateTime datePaid;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderLine> orderLines = new HashSet<>();

    @Column(name = "ORDER_STATUS_CODE")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "CLIENT_ID")
    private Long customer;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_PAYMENT_METHOD_ID")
    private ClientPaymentMethods clientPaymentMethods;

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
