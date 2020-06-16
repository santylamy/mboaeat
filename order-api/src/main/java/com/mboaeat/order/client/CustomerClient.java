package com.mboaeat.order.client;

import com.mboaeat.common.dto.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface CustomerClient {
    @GetMapping(value = "/api/v1.0/clients/user/{userId}")
    Client findClientByUser(@PathVariable("userId") Long userId);
}
