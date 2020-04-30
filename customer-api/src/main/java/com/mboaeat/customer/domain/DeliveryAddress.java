package com.mboaeat.customer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;

@Data
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue(value = "DELIVERY")
public class DeliveryAddress extends Address{
}
