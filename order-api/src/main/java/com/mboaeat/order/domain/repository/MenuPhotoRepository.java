package com.mboaeat.order.domain.repository;

import com.mboaeat.order.domain.menu.MenuPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuPhotoRepository extends JpaRepository<MenuPhoto, Long> {

    public MenuPhoto findByReferenceCloud(String referenceCloud);
}
