package com.mboaeat.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "CLIENT_ORDERS_DELIVERY")
public class ClientOrderDelivery implements Serializable {

    @Column(name = "CLIENT_ORDER_DELIVERY_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorClientOrderDelivery")
    @SequenceGenerator(name = "idGeneratorClientOrderDelivery", sequenceName = "SEQ_CLIENT_ORDER_DELIVERY", allocationSize = 1)
    @Id
    private Long id;

    @JoinColumn(name = "ORDER_ID")
    @ManyToOne
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "DELIVERY_STATUS_CODE")
    private DeliveryStatus deliveryStatus;
}
