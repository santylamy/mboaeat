package com.mboaeat.account.controller;

import com.mboaeat.account.service.AccountService;
import com.mboaeat.common.dto.request.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1.0/users/register")
@Tag(name = "User API")
@Schema(name = "Register API",description = "Provides a list methods for registration")
@Validated
public class RegisterRestController {

    private final AccountService accountService;

    @Autowired
    public RegisterRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Register the user", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form or e-mail exists"),
            @ApiResponse(responseCode = "409", description = "The e-mail exists")
    })
    @PostMapping
    public ResponseEntity<Void> register(
            @Parameter(description = "Registration form", required = true)
            @Valid @RequestBody RegisterDTO registerDTO,
            final UriComponentsBuilder uriComponentsBuilder){
        accountService.createAccount(registerDTO.getEmail(), registerDTO.getPassword());
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponentsBuilder.path("/register/successfully").build().toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

}
