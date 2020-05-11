package com.mboaeat.order.domain.repository;

import com.mboaeat.common.AbstractTest;
import com.mboaeat.common.jpa.AbstractRepositoryTest;
import com.mboaeat.order.domain.Product;
import com.mboaeat.order.domain.ProductName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.assertj.core.api.Assertions.assertThat;

@EnableJpaAuditing
class BaseEntityRepositoryTest extends AbstractOrderTest {

    @Autowired
    BaseEntityRepository repository;

    @Test
    public void testCreateBaseEntity(){
        Product product = Product
                .builder()
                .productName(ProductName.builder().name("Champignons").build())
                .build();
        Product productToSaved = repository.save(product);

        assertThat(productToSaved).isNotNull();
    }

}