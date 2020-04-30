package com.mboaeat.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressStreet {

    @Column(name = "STREET_NAME_ONE")
    private String name_one;

    @Column(name = "STREET_NAME_TWO")
    private String name_two;

    @Column(name = "STREET_POST_BOX")
    private String postBox;

    @Column(name = "STREET_CITY")
    private String city;
}
