package com.mboaeat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Tag(name = "Client data")
public class Client extends AbstractBaseDTO {

    @Schema(description = "The client name")
    private String name;

    @NotNull
    @Valid
    @Schema(description = "The client address", required = true)
    private AddressDTO address;
}
