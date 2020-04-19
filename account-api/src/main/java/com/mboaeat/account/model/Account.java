package com.mboaeat.account.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements Serializable {

    @Column(name = "ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorAccount")
    @SequenceGenerator(name = "idGeneratorAccount", sequenceName = "SEQ_ACCOUNT", allocationSize = 100)
    @Id
    private Long id;

    @JoinColumn(name = "PERSON_ID")
    @OneToOne(cascade = {CascadeType.ALL})
    protected NaturalPerson naturalPerson;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    protected Account(){}
}
