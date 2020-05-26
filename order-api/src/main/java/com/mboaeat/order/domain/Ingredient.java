package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Embeddable
public class Ingredient {

    @Column(name = "INGREDIENT_NAME", nullable = false)
    private String name;

    @Column(name = "INGREDIENT_KEY", insertable = false, updatable = false)
    private Integer key;

}
