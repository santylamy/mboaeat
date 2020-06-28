package com.mboaeat.order.domain.product;

import com.mboaeat.order.domain.BaseEntity;
import com.mboaeat.domain.TranslatableString;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
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
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "french", column = @Column(name = "PRODUCT_NAME_FR")),
                    @AttributeOverride(name = "english", column = @Column(name = "PRODUCT_NAME_EN"))
            }
    )
    private TranslatableString productName;

    @AttributeOverrides({
            @AttributeOverride(name = "french", column = @Column(name = "PRODUCT_DESC_FR")),
            @AttributeOverride(name = "english", column = @Column(name = "PRODUCT_DESC_EN"))
    }
    )
    private TranslatableString description;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_TYPE_CODE")
    private ProductType category;

    @Builder
    public Product(TranslatableString productName, TranslatableString description, ProductType category, ProductPrice productPrice){
        this.productName = productName;
        this.description = description;
        this.category = category;
        addProductPrice(productPrice);
    }

    private void addProductPrice(ProductPrice productPrice) {
        if (productPrice != null) {
            pricesHistory.addPrice(productPrice);
        }
    }

    public ProductPrice getCurrentPrice(){
        return pricesHistory.getCurrent();
    }

    public List<ProductPrice> applyProductPriceChangeCommand(ChangeProductPriceCollectionCommand command, boolean commit) {
        return pricesHistory.apply(command, commit);
    }
}
