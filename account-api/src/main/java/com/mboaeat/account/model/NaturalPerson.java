package com.mboaeat.account.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "PERSONS")
public class NaturalPerson implements Serializable {

    @Id
    @Column(name = "PERSON_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorNaturalPerson")
    @SequenceGenerator(name = "idGeneratorNaturalPerson", sequenceName = "SEQ_PERSONS", allocationSize = 100)
    private Long personId;

    @Embedded
    private EmailAddress emailAddress;

    @Embedded
    private PersonName personName;
}
