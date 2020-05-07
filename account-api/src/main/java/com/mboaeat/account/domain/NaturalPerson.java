package com.mboaeat.account.domain;

import com.mboaeat.common.domain.EmailAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "PERSONS")
@NoArgsConstructor
@AllArgsConstructor
public class NaturalPerson implements Serializable {

    @Id
    @Column(name = "PERSON_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorNaturalPerson")
    @SequenceGenerator(name = "idGeneratorNaturalPerson", sequenceName = "SEQ_PERSONS", allocationSize = 1)
    private Long personId;

    @Embedded
    private EmailAddress emailAddress;

    @Embedded
    private PersonName personName;
}
