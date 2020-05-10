package com.mboaeat.order.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorBase")
    //@SequenceGenerator(name = "idGeneratorBase", sequenceName = "SEQ_BASES", allocationSize = 1)

    @GeneratedValue(generator = "idGeneratorBase")
    @GenericGenerator(name = "idGeneratorBase",
            parameters = {
                @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_BASES"),
                @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1")
            },
            strategy = "com.mboaeat.order.domain.IdentifierGenerator")
    protected Long id;

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date createdDate;

    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    protected Date modifiedDate;

    protected ID getId(){
        return (ID) id;
    }

}
