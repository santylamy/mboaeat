package com.mboaeat.order.domain.menu;

import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Embeddable
public class MenuPriceOptionCollection implements Serializable {

    @ElementCollection
    @CollectionTable(name = "MENU_PRICE_OPTIONS", joinColumns = @JoinColumn(name = "MENU_PRICE_ID"))
    @CollectionId(
            columns = @Column(name = "MENU_PRICE_OPTION_ID"),
            type = @Type(type = "long"),
            generator = "idGeneratorMenuPriceOption"
    )
    @SequenceGenerator(name = "idGeneratorMenuPriceOption", sequenceName = "SEQ_MENU_PRICE_OPTIONS", allocationSize = 1)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MenuPriceOption> priceOptions = newArrayList();
}
