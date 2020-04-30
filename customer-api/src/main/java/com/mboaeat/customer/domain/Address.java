package com.mboaeat.customer.domain;

import com.mboaeat.common.domain.MobilePhoneNumber;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "ADDRESS")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "ADDRESS_TYPE", discriminatorType = DiscriminatorType.STRING)
public class Address {

    @Column(name = "ADDRESS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorAddress")
    @SequenceGenerator(name = "idGeneratorAddress", sequenceName = "SEQ_ADDRESSES", allocationSize = 100)
    @Id
    private Long id;

    @Embedded
    private PersonAddressName personAddressName;

    @Column(name = "COUNTRY")
    private String country;

    @Embedded
    private AddressStreet addressStreet;

    @Embedded
    private MobilePhoneNumber mobilePhoneNumber;

}
