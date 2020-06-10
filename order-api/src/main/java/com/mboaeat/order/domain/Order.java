package com.mboaeat.order.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.mboaeat.domain.CollectionsUtils.newHashSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    private Amount totalAmount = Amount.zero();

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "TOTAL_PRICE_TVA")
    )
    @Builder.Default
    private Amount totalAmountTva = Amount.zero();

    @Column(name = "DATE_ORDER_PLACED")
    @Builder.Default
    private LocalDateTime datePlaced = LocalDateTime.now();

    @Column(name = "DATE_ORDER_PAID")
    private LocalDateTime datePaid;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderLine> orderLines = newHashSet();

    @Column(name = "ORDER_STATUS_CODE")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @Column(name = "CLIENT_ID")
    private Long customer;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_PAYMENT_METHOD_ID")
    private ClientPaymentMethods clientPaymentMethods;

    public Order addOrderLine(OrderLine orderLine) {
        orderLine.setOrder(this);
        this.totalAmount = totalAmount.add(orderLine.getPrice());
        this.totalAmountTva = totalAmountTva.add(totalAmount);
        return this;
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
