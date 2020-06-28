package com.mboaeat.customer.controller;

import com.mboaeat.common.dto.TranslateDto;
import com.mboaeat.common.translation.Language;
import com.mboaeat.customer.service.CountryCityDistrictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
@Tag(name = "Country City District API")
@Schema(description = "Provides a list of methods that retrieve country, city, district and their data")
@Validated
public class CountryCityDistrictController {

    private final CountryCityDistrictService service;

    public CountryCityDistrictController(CountryCityDistrictService service) {
        this.service = service;
    }

    @Operation(summary = "Get country translate by language")
    @GetMapping("/countries/default")
    public TranslateDto getCountry(@RequestParam(name = "lang") String lang){
        return service.getCountry(Language.toLanguage(lang));
    }

    @Operation(summary = "Get all cities districts translate by language")
    @GetMapping("/cities/districts")
    public List<TranslateDto> allTranslateCitiesDistrict(@RequestParam(name = "lang") String lang){
        return service.getAllCityDistrict(Language.toLanguage(lang));
    }

}
