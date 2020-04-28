package com.mboaeat.common.dto.request;

import com.mboaeat.common.dto.AbstractBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Tag(name = "Data to chage user address email and user name")
public class ChangeEmailUserInfo extends AbstractBaseDTO {

    @NotBlank
    @Email
    @Schema(name = "The user's new e-mail", required = true)
    private String email;

    @NotBlank
    @Schema(name = "The user's first name")
    private String firstName;

    @NotBlank
    @Schema(name = "The user's last name")
    private String lastName;

    @NotBlank
    @Schema(name = "The user's middle name")
    private String middleName;

}
