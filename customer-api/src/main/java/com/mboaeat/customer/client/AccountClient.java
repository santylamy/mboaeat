package com.mboaeat.customer.client;

import com.mboaeat.common.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping(value = "/api/v1.0/users/user/{userId}")
    User findUserById(@PathVariable("userId") Long userId);
}
