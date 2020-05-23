package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AuditEntity {

    @Id
    @GeneratedValue(generator = "idGeneratorBase")
    @GenericGenerator(name = "idGeneratorBase",
            parameters = {
                @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SEQ_BASES"),
                @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1")
            },
            strategy = "com.mboaeat.order.domain.IdentifierGenerator")
    protected Long id;

    public ID getId(){
        return (ID) id;
    }

}
