package com.mboaeat.order.domain;

import com.mboaeat.common.AbstractChangePeriodicalCollectionCommand;
import lombok.Builder;
import lombok.Data;

@Data
public class ChangeProductPriceCollectionCommand extends AbstractChangePeriodicalCollectionCommand<ProductPrice, PeriodByDay> {

    private Amount amount;
    private Product product;

    @Builder
    public ChangeProductPriceCollectionCommand(PeriodByDay period, Product product, Amount amount) {
        super(period);
        this.product = product;
        this.amount = amount;
    }

    @Override
    public ProductPrice change(ProductPrice productPrice) {
        return create(productPrice.getPeriod());
    }

    @Override
    public ProductPrice create(PeriodByDay period) {
        return ProductPrice.
                builder()
                .amount(amount)
                .product(product)
                .period(period)
                .build();
    }
}
