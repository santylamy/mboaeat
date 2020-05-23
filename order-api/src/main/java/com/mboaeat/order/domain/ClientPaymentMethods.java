package com.mboaeat.order.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "CLIENT_PAYMENT_METHODS")
public class ClientPaymentMethods implements Serializable {

    @Column(name = "CLIENT_PAYMENT_METHOD_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorClientPaymentMethods")
    @SequenceGenerator(name = "idGeneratorClientPaymentMethods", sequenceName = "SEQ_CLIENT_APY_METHOD", allocationSize = 1)
    @Id
    private Long id;

    @Column(name = "CLIENT_ID")
    private Long customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_STATUS_CODE")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "METHOD_CODE")
    private MethodCode methodCode;
}
