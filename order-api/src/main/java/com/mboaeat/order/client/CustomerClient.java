package com.mboaeat.order.client;

import com.mboaeat.common.dto.Client;
import com.mboaeat.common.dto.TranslateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping(value = "/api/v1.0/clients/user/{userId}")
    Client findClientByUser(@PathVariable("userId") Long userId);

    @GetMapping(value = "/api/v1.0/countries/default")
    TranslateDto getDefaultCountry(@RequestParam(name = "lang") String lang);

    @GetMapping(value = "/api/v1.0/cities/districts")
    List<TranslateDto> getAllCitiesDistricts(@RequestParam(name = "lang") String lang);

}
