package com.mboaeat.account.controller;

import com.mboaeat.account.service.AccountService;
import com.mboaeat.common.dto.request.ChangeEmailUserInfo;
import com.mboaeat.common.dto.request.ChangePasswordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1.0/users/account/update")
@Tag(name = "Settings API", description = "Provides a list of methods that manage user settings")
@Validated
public class SettingsRestController {

    private final AccountService accountService;

    @Autowired
    public SettingsRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "It changes the user's email and sends a token to the mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form"),
            @ApiResponse(responseCode = "404", description = "No user found"),
            @ApiResponse(responseCode = "409", description = "The e-mail exists")
    })
    @PutMapping(value = "/detail")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmail(
            @Parameter(description = "Form of e-mail change", required = true)
            @RequestBody @Valid final ChangeEmailUserInfo changeEmailUserInfo
    ) {
        accountService.updateAccount(
                changeEmailUserInfo.asLongId(),
                changeEmailUserInfo.getEmail(),
                changeEmailUserInfo.getFirstName(),
                changeEmailUserInfo.getLastName(),
                changeEmailUserInfo.getMiddleName()
        );
    }

    @Operation(summary = "It changes the user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect data in the form or password is incorrect"),
            @ApiResponse(responseCode = "404", description = "No user found")
    })
    @PutMapping(value = "/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(
            @Parameter(description = "Form of password change", required = true)
            @RequestBody @Valid final ChangePasswordDTO changePasswordDTO
    ) {
        accountService.updatePassword(
                changePasswordDTO.asLongId(),
                changePasswordDTO.getOldPassword(),
                changePasswordDTO.getNewPassword(),
                changePasswordDTO.getConfirmPassword()
        );
    }

}
