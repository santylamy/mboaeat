package com.mboaeat.order.domain.product;

import com.mboaeat.domain.CollectionsUtils;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Embeddable
public class ProductCollection {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MENU_PRODUCT",
        joinColumns = {@JoinColumn(name = "MENU_ID", referencedColumnName = "MENU_ID")},
        inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")}
    )
    private List<Product> menuProducts = newArrayList();

    public void link(Product product) {
        menuProducts.add(product);
    }

    public void links(List<Product> products){
        if (!CollectionsUtils.isEmpty(products)) {
            menuProducts.addAll(products);
        }
    }
}
