package com.mboaeat.account.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
@Data
@Builder
public class Password {

    @Column(name = "PWD")
    private String value;

    @Transient
    private String confirm;
}
