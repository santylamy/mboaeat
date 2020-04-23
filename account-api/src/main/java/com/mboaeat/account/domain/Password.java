package com.mboaeat.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Password {

    @Column(name = "PWD")
    private String value;

    @Column(name = "HASH")
    private String hash;

    @Transient
    private String confirm;
}
