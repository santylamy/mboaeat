package com.mboaeat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Tag(name = "Address data")
public class AddressDTO extends AbstractBaseDTO{

    @NotBlank
    @Schema(description = "The client address first name", required = true)
    private String name;

    @NotBlank
    @Schema(description = "The client address last name", required = true)
    private String lastName;

    @Schema(description = "The client address fixed phone number")
    private String fixedNumber;

    @Schema(description = "The client address mobile phone number")
    private String mobileNumber;

    @NotBlank
    @Schema(description = "The client address country", required = true, accessMode = Schema.AccessMode.READ_ONLY, defaultValue = "Cameroon")
    private String country;

    @NotBlank
    @Schema(description = "The client address street 1", required = true)
    private String streetNameOne;

    @Schema(description = "The client address street 2")
    private String streetNameTwo;

    @Schema(description = "The client address zip code")
    private String postBox;

    @Schema(description = "The client address city", required = true)
    private String city;

    @Schema(description = "The client address e-mail")
    @Email
    private String email;

    @Schema(description = "The client address type", required = true)
    private AddressType type;
}
