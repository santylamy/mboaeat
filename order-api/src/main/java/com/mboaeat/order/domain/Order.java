package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorOrder")
    @SequenceGenerator(name = "idGeneratorOrder", sequenceName = "SEQ_ORDERS", allocationSize = 1)
    private Long id;

}
