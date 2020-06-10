package com.mboaeat.order.domain;

import com.mboaeat.common.jpa.AbstractRepositoryTest;
import com.mboaeat.order.domain.product.Product;
import com.mboaeat.order.domain.repository.BaseEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.assertj.core.api.Assertions.assertThat;

@EnableJpaAuditing
class BaseEntityRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    BaseEntityRepository repository;

    @Test
    public void testCreateBaseEntity(){
        Product product = Product
                .builder()
                .productName(Name.builder().nameFr("Champignons").build())
                .build();
        Product productToSaved = repository.save(product);

        assertThat(productToSaved).isNotNull();
    }

}