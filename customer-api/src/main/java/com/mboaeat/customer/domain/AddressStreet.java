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

    @Column(name = "STREET_DESC_ONE")
    private String streetDescOne;

    @Column(name = "STREET_DESC_TWO")
    private String streetDescTwo;

    @Column(name = "STREET_POST_BOX")
    private String postBox;

    @Column(name = "STREET_CITY")
    private String city;
}
