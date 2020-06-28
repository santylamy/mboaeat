package com.mboaeat.order.domain.service;


import com.mboaeat.common.jpa.AbstractRepositoryTest;
import com.mboaeat.domain.TranslatableString;
import com.mboaeat.order.domain.*;
import com.mboaeat.order.domain.product.Product;
import com.mboaeat.order.domain.product.ProductPrice;
import com.mboaeat.order.domain.product.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackageClasses ={ProductService.class})
class ProductServiceTest extends AbstractRepositoryTest {

    @Autowired
    ProductService productService;

    @Test
    public void testCreateBaseEntity(){
        TranslatableString productName = TranslatableString.builder().french("Champignons").build();
        Product product = Product
                .builder()
                .productName(productName)
                .build();
        Product productToSaved = productService.createProduct(product);

        assertThat(productToSaved).isNotNull();
        assertThat(productToSaved.getProductName()).isEqualTo(productName);
        assertThat(productToSaved.getPricesHistory().getProductPrices()).isEmpty();
    }

    @Test
    public void testCreateBaseEntity_With_ProductPrice(){
        Product product = Product
                .builder()
                .productName(TranslatableString.builder().french("Champignons").build())
                .build();

        ProductPrice productPrice = ProductPrice.builder().product(product).amount(Amount.one()).period(PeriodByDay.periodByDayStartingToday()).build();
        product.getPricesHistory().getProductPrices().add(productPrice);
        Product productToSaved = productService.createProduct(product);

        assertThat(productToSaved).isNotNull();
        assertThat(productToSaved.getPricesHistory().getProductPrices()).isNotEmpty();
        assertThat(productToSaved.getProductName()).isEqualTo(TranslatableString.builder().french("Champignons").build());
        assertThat(product.getPricesHistory().getCurrent().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingToday());
    }

    @Test
    public void testCreateBaseEntity_With_ProductPrice_change(){
        Product product = Product
                .builder()
                .productName(TranslatableString.builder().french("Champignons").build())
                .build();

        ProductPrice productPrice = ProductPrice.builder().product(product).amount(Amount.one()).period(
                PeriodByDay.builder().startDate(LocalDate.now().with(TemporalAdjusters.firstDayOfYear())).build()
        ).build();
        product.getPricesHistory().getProductPrices().add(productPrice);
        Product productToSaved = productService.createProduct(product);

        assertThat(productToSaved).isNotNull();
        assertThat(productToSaved.getPricesHistory().getProductPrices()).isNotEmpty();
        assertThat(productToSaved.getProductName()).isEqualTo(TranslatableString.builder().french("Champignons").build());


        ProductPrice nextProductPrice = ProductPrice
                .builder()
                .product(product)
                .amount(Amount.builder().value(BigDecimal.valueOf(25)).build())
                .period(PeriodByDay.periodByDayStartingToday()).build();

        productService.changeProductPrice(product.getId(), nextProductPrice);

        Product refreshProduct = productService.getProduct(product.getId()).get();

        assertThat(refreshProduct.getPricesHistory().gePeriods()).hasSize(2);
        assertThat(refreshProduct.getPricesHistory().getCurrent().getPeriod()).isEqualTo(PeriodByDay.periodByDayStartingToday());
    }

    @Test
    public void testCreateBaseEntity_With_Product_content_change(){
        Product product = Product
                .builder()
                .productName(TranslatableString.builder().french("Champignons").build())
                .build();

        ProductPrice productPrice = ProductPrice.builder().product(product).amount(Amount.one()).period(
                PeriodByDay.builder().startDate(LocalDate.now().with(TemporalAdjusters.firstDayOfYear())).build()
        ).build();
        product.getPricesHistory().getProductPrices().add(productPrice);
        Product productToSaved = productService.createProduct(product);

        assertThat(productToSaved).isNotNull();
        assertThat(productToSaved.getPricesHistory().getProductPrices()).isNotEmpty();
        assertThat(productToSaved.getProductName()).isEqualTo(TranslatableString.builder().french("Champignons").build());


        Product refreshProduct = productService.getProduct(productToSaved.getId()).get();

        refreshProduct.setDescription( TranslatableString.builder().french("Champignons de douala").build());
        refreshProduct.setCategory(ProductType.VEGETABLE);

        productService.updateProduct(refreshProduct);

        refreshProduct = productService.getProduct(productToSaved.getId()).get();

        assertThat(refreshProduct.getDescription().getFrench()).isEqualTo("Champignons de douala");
        assertThat(refreshProduct.getCategory()).isEqualTo(ProductType.VEGETABLE);
    }



}