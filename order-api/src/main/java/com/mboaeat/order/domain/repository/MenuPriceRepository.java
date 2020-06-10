package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.menu.MenuPrice;
import com.mboaeat.order.domain.menu.MenuPriceOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuPriceRepository extends JpaRepository<MenuPrice, Long> {

    @Query("select mp from MenuPrice mp where :mpo in elements(mp.priceOptionCollection.priceOptions) ")
    public MenuPrice getByMenuPriceOption(@Param("mpo") MenuPriceOption option);

}
