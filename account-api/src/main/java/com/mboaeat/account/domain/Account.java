package com.mboaeat.account.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ACCOUNTS")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements Serializable {

    @Column(name = "ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorAccount")
    @SequenceGenerator(name = "idGeneratorAccount", sequenceName = "SEQ_ACCOUNTS", allocationSize = 1)
    @Id
    private Long id;

    @JoinColumn(name = "PERSON_ID")
    @OneToOne(cascade = {CascadeType.ALL})
    protected NaturalPerson naturalPerson;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;
}
