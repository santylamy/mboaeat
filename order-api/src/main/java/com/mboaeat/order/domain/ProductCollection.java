package com.mboaeat.order.domain;

import lombok.Data;
import org.assertj.core.util.Sets;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mboaeat.common.CollectionsUtils.newArrayList;

@Data
@Embeddable
public class ProductCollection {

    @OneToMany
    @JoinTable(name = "MENU_PRODUCT",
        joinColumns = {@JoinColumn(name = "MENU_ID", referencedColumnName = "MENU_ID")},
        inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")}
    )
    private List<Product> menuProducts = newArrayList();

    public void link(Product product) {
        menuProducts.add(product);
    }
}
