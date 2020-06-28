package com.mboaeat.customer.repository;

import com.mboaeat.customer.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, String> {
}
