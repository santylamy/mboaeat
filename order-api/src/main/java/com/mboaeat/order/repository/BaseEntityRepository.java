package com.mboaeat.order.repository;

import com.mboaeat.order.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepository extends JpaRepository<BaseEntity<Long>, Long> {
}
