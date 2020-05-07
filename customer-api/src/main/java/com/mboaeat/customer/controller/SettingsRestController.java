package com.mboaeat.customer.controller;

import com.mboaeat.common.dto.Client;
import com.mboaeat.common.dto.User;
import com.mboaeat.customer.client.AccountClient;
import com.mboaeat.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1.0/clients/update")
@Schema(name = "Settings Client API", description = "Provides a list of methods that manage client settings")
@Tag(name = "Client API")
@Validated
public class SettingsRestController {

    private final CustomerService customerService;

    private final AccountClient accountClient;

    public SettingsRestController(CustomerService customerService, AccountClient accountClient) {
        this.customerService = customerService;
        this.accountClient = accountClient;
    }

    @Operation(summary = "Update the client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form"),
            @ApiResponse(responseCode = "404", description = "Incorrect url")
    })
    @PutMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClient(
            @Parameter(description = "The client id", required = true)
            @PathVariable("userId") final Long userId,
            @Parameter(description = "From client change", required = true)
            @RequestBody @Valid Client client
    ){
        User user = accountClient.findUserById(userId);
        customerService.updateCustomer(user.asLongId(), client);
    }

}
