package com.mboaeat.customer.domain;

import com.mboaeat.common.domain.EmailAddress;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;

@Data
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue(value = "BILLING")
public class BillingAddress extends Address {

    @Embedded
    private EmailAddress emailAddress;
}
