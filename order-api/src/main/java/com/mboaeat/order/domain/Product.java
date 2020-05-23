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
    private final ProductPriceCollection pricesHistory = new ProductPriceCollection();

    @Embedded
    private ProductName productName;

    @Column(name = "PRODUCT_DESC")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_TYPE_CODE")
    private ProductType category;

    public ProductPrice getCurrentPrice(){
        return pricesHistory.getCurrent();
    }

    public List<ProductPrice> applyProductPriceChangeCommand(ChangeProductPriceCollectionCommand command, boolean commit) {
        return pricesHistory.apply(command, commit);
    }
}
