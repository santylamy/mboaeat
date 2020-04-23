package com.mboaeat.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@Tag(name = "User registration data")
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @Email
    @NotBlank
    @Schema(description = "The user's e-mail", required = true)
    private String email;

    @NotBlank
    @Schema(description = "The user's password", required = true)
    private String password;
}
