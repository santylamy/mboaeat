package com.mboaeat.order.domain;


import com.mboaeat.order.domain.product.ProductPrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


class ProductPriceTest {

    @Test
    public void create_PeriodByToday(){
        ProductPrice.builder().amount(Amount.builder().value(BigDecimal.valueOf(5)).build()).build();
    }

}