package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.menu.MenuDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDistrictRepository extends JpaRepository<MenuDistrict, Long> {

    public MenuDistrict findByDistrictNisCode(String districtNisCode);
}
