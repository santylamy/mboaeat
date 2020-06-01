package com.mboaeat.order.domain;

import com.mboaeat.domain.AbstractPeriodicalCollection;
import com.mboaeat.domain.ChangePeriodicalCollectionCommand;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.mboaeat.domain.CollectionsUtils.newArrayList;

@Data
@Embeddable
public class ProductPriceCollection extends AbstractPeriodicalCollection<ProductPrice, PeriodByDay> {


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProductPrice> productPrices = newArrayList();

    @Override
    protected List<ProductPrice> getPeriodicals() {
        return productPrices;
    }

    @Override
    public ProductPriceCollection copy() {
        ProductPriceCollection priceCollection = new ProductPriceCollection();
        priceCollection.productPrices = new ArrayList<>(productPrices);
        return priceCollection;
    }

    public void addPrice(ProductPrice price){
        productPrices.add(price);
    }

    @Override
    public List<ProductPrice> apply(ChangePeriodicalCollectionCommand<ProductPrice, PeriodByDay> command, boolean commit) {
        return super.apply(command, commit);
    }


}
