package com.mboaeat.customer.service;

import com.mboaeat.common.dto.TranslateDto;
import com.mboaeat.common.translation.Language;
import com.mboaeat.customer.domain.CityDistrict;
import com.mboaeat.customer.domain.Country;
import com.mboaeat.customer.repository.CityDistrictRepository;
import com.mboaeat.customer.repository.CityRepository;
import com.mboaeat.customer.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CountryCityDistrictService {

    private final CityRepository cityRepository;
    private final CityDistrictRepository cityDistrictRepository;
    private final CountryRepository countryRepository;

    public CountryCityDistrictService(CityRepository cityRepository, CityDistrictRepository cityDistrictRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.cityDistrictRepository = cityDistrictRepository;
        this.countryRepository = countryRepository;
    }


    public Country getDefaultCountry(){
        return countryRepository.lookUpCameroon().get();
    }

    public TranslateDto getCountry(Language language){
        final Country country = getDefaultCountry();
        return TranslateDto
                .builder()
                .reference(country.getNisCode())
                .description(country.getName().asString(language))
                .build();
    }

    public List<TranslateDto> getAllCityDistrict(Language language){
       final List<CityDistrict> cityDistricts = cityDistrictRepository.findAll();

       return cityDistricts
               .stream()
               .filter(CityDistrict::isDeliverable)
               .map(
                       cityDistrict -> TranslateDto
                               .builder()
                               .reference(cityDistrict.getDistrictCode())
                               .description(cityDistrict.getDistrictDescription().asString(language))
                               .build()
               ).collect(Collectors.toList());
    }

}
