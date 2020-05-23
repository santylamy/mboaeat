package com.mboaeat.order.domain;

import lombok.Data;
import org.assertj.core.util.Sets;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Data
@Embeddable
public class ProductCollection {

    @ElementCollection
    //@CollectionTable(name = "MENU_PRODUCTS", joinColumns = @JoinColumn(name = "MENU_ID"))
    //@MapKeyJoinColumn(name = "")
    @OneToMany
    private Map<Menu, Product> products = Map.of();// = Maps.newHashMap();
}
