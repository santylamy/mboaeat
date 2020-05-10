package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")
@AttributeOverride(
        name = "id",
        column = @Column(name = "PRODUCT_ID")
)
public class Product extends BaseEntity<Long> {

    //@Id
    //@Column(name = "PRODUCT_ID")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGeneratorProduct")
    //@SequenceGenerator(name = "idGeneratorProduct", sequenceName = "SEQ_PRODUCTS", allocationSize = 1)
    //private Long id;

    @Embedded
    private ProductName productName;

    @ElementCollection
    @CollectionTable(name = "PRODUCTS_INGREDIENTS", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<ProductIngredient> ingredients;

}
