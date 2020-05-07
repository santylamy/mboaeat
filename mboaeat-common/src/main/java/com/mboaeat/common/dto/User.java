package com.mboaeat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Schema(name = "UserInfo" , description = "User data")
public class User extends AbstractBaseDTO {

    @Schema(description = "The user's e-mail", required = true)
    private String email;

    @Schema(description = "The user's name", required = true)
    private String firstName;

    @Schema(description = "The user's last name", required = true)
    private String lastName;

    @Schema(description = "The user's middle name", required = true)
    private String middleName;
}
