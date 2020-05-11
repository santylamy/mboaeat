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

    @Embedded
    private Amount price;

    @Embedded
    private ProductName productName;

    @Column(name = "PRODUCT_DESC")
    private String description;

    @ElementCollection
    @CollectionTable(name = "PRODUCTS_INGREDIENTS", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<ProductIngredient> ingredients;

}
