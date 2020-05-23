package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@Embeddable
public class ProductIngredient {

    @Column(name = "INGREDIENT_NAME", nullable = false)
    private String name;
}
