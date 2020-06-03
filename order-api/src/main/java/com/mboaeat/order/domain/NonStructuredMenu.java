package com.mboaeat.order.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("NON_STRUCTURED")
public class NonStructuredMenu extends Menu {

    @Embedded
    @Builder.Default
    private ProductCollection productCollection = new ProductCollection();

    public void addProducts(Product... products) {
        addProducts(Arrays.asList(products));
    }

    public void addProducts(List<Product> products) {
        productCollection.links(products);
    }

    public void addProduct(Product product) {
        productCollection.link(product);
    }

}
