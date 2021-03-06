package com.mboaeat.customer.domain;

import com.mboaeat.common.domain.FixedPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENTS")
public class Customer implements Serializable {

    @Id
    @Column(name = "CLIENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorClient")
    @SequenceGenerator(name = "idGeneratorClient", sequenceName = "SEQ_CLIENTS", allocationSize = 1)
    private Long id;

    @Embedded
    private ClientName clientName;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Embedded
    private FixedPhoneNumber fixedPhoneNumber;

    @Column(name = "ACCOUNT_ID")
    private Long user;
}
