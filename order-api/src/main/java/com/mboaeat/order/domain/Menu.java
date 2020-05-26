package com.mboaeat.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Arrays;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "MENUS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "MENU_TYPE")
@AttributeOverride(
        name = "id",
        column = @Column(name = "MENU_ID")
)
public abstract class Menu extends BaseEntity<Long>  {

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "MENU_PRICE")
    )
    protected Amount price;

    @Embedded
    protected ProductCollection productCollection = new ProductCollection();

    public void productsLink(Product... products) {
        getProductCollection().setMenuProducts(Arrays.asList(products));
    }

    public void productLink(Product product) {
        productCollection.link(product);
    }
}
