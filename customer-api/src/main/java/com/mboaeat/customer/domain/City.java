package com.mboaeat.customer.domain;

import com.mboaeat.customer.repository.CountryRepository;
import com.mboaeat.domain.TranslatableString;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "CITIES")
@Configurable
public class City implements Serializable {

    @Id
    @Column(name = "NIS_CODE", insertable = false, updatable = false)
    private String nisCode;

    @Embedded
    private TranslatableString name;

    @OneToMany(mappedBy = "city")
    private List<CityDistrict> cityDistricts;

    public Country getCountry() {
       return countryRepository.lookUpCameroon().orElse(null);
    }

    @Autowired
    @Transient
    private CountryRepository countryRepository;


}
