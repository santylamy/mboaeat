package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
