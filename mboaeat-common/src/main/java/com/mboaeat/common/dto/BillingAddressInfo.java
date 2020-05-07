package com.mboaeat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@NoArgsConstructor
@Schema
public class BillingAddressInfo extends AddressDTO {

    @Schema(description = "The client address fixed phone number")
    private String fixedNumber;

    @Schema(description = "The client address e-mail")
    @NotBlank
    @Email
    private String email;
}
