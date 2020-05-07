package com.mboaeat.api.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayServiceController {

    @GetMapping(path = "fallback")
    public String fallback(){
        return "{}";
    }
}
