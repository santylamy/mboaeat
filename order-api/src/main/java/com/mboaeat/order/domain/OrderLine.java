package com.mboaeat.order.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ORDERLINES")
@AttributeOverride(
        name = "id",
        column = @Column(name = "ORDER_LINE_ID")
)
public class OrderLine extends BaseEntity<Long> {

}
