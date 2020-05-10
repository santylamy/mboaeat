package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PAYMENTS_MODE")
@AttributeOverride(
        name = "id",
        column = @Column(name = "PAY_MOD_ID")
)
public class PaymentMode extends BaseEntity {

}
