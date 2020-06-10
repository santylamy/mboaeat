package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.product.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
}
