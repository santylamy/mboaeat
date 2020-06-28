package com.mboaeat.customer.domain;

import com.mboaeat.domain.TranslatableString;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "COUNTRIES")
public class Country implements Serializable {

    public static final String CAMEROON_NIS_CODE = "00100";

    @Id
    @Column(name = "NIS_CODE", insertable = false, updatable = false)
    private String nisCode;

    @Embedded
    private TranslatableString name;
}
