package com.mboaeat.account.controller;

import com.mboaeat.account.hateoas.UserModel;
import com.mboaeat.account.hateoas.assembler.UserModelAssembler;
import com.mboaeat.account.service.AccountService;
import com.mboaeat.common.dto.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping(value = "/api/v1.0/users")
@Tag(name = "User API", description = "Provides a list of methods that retrieve users and their data")
@Validated
public class UserRestController {

    private final AccountService accountService;
    private final UserModelAssembler userModelAssembler;

    public UserRestController(AccountService accountService, UserModelAssembler userModelAssembler) {
        this.accountService = accountService;
        this.userModelAssembler = userModelAssembler;
    }

    @Operation(summary = "Get user profile by email")
    @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "No user found") })
    @GetMapping(value = "/{username}")
    public UserModel getProfile(
            @Parameter(description = "The user's e-mail", required = true)
            @PathVariable("username") @Valid @Email final String username
    ) {
        User user = accountService.getUserByEmail(username);
        UserModel userModel = userModelAssembler.toModel(user);
        userModel.add(
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder.methodOn(UserRestController.class).getProfile(user.getEmail())).withSelfRel()
        );
        return userModel;
    }
}