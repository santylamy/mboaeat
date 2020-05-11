package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseEntityRepository extends JpaRepository<BaseEntity<Long>, Long> {
}
