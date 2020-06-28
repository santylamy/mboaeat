package com.mboaeat.customer.repository;

import com.mboaeat.customer.domain.Country;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.mboaeat.customer.domain.Country.*;

public interface CountryRepository extends JpaRepository<Country, String> {

    public default Optional<Country> lookUpCameroon(){
        String paddedNisCode = StringUtils.leftPad(CAMEROON_NIS_CODE, 5, '0');
        return findByNisCode(paddedNisCode);
    }

    public Optional<Country> findByNisCode(String nisCode);
}
