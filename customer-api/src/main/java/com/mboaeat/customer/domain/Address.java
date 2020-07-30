package com.mboaeat.customer.domain;

import com.mboaeat.common.domain.MobilePhoneNumber;
import com.mboaeat.common.dto.type.AddressTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESS")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "ADDRESS_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force = true)
public abstract class Address {

    @Column(name = "ADDRESS_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorAddress")
    @SequenceGenerator(name = "idGeneratorAddress", sequenceName = "SEQ_ADDRESSES", allocationSize = 1)
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

    @Column(name = "ADDRESS_TYPE", updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    private AddressTypeInfo addressTypeInfo;

}
