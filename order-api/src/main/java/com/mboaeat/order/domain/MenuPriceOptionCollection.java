package com.mboaeat.order.domain;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.integration.annotation.Default;

import javax.persistence.*;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Embeddable
public class MenuPriceOptionCollection {

    @ElementCollection
    @CollectionTable(name = "MENU_PRICE_OPTION", joinColumns = @JoinColumn(name = "MENU_PRICE_ID"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MenuPriceOption> priceOptions = newArrayList();
}
