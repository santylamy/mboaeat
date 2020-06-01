package com.mboaeat.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Description implements Serializable {

    @Column(name = "DESC_FR")
    private String descFr;

    @Column(name = "DESC_EN")
    private String descEn;

}
