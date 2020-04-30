package com.mboaeat.customer.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@Embeddable
public class PersonAddressName {

    @Column(name = "NAME")
    private String name;

    @Column(name = "FIRST_NAME")
    private String firstName;
}
