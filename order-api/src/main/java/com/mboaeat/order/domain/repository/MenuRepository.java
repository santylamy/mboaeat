package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
