package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.menu.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    MenuCategory getByCode(String code);
}
