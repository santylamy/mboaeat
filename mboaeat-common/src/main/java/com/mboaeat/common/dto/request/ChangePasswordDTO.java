package com.mboaeat.common.dto.request;

import com.mboaeat.common.dto.AbstractBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@NoArgsConstructor
public class ChangePasswordDTO extends AbstractBaseDTO {

    @NotBlank
    @Schema(name = "The user's password", required = true)
    private String oldPassword;

    @NotBlank
    @Schema(name = "The user's new password", required = true)
    private String newPassword;

    @NotBlank
    @Schema(name = "Again the user's new password", required = true)
    private String confirmPassword;
}
