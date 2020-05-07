package com.mboaeat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetInfo {

    @NotBlank
    @Schema(description = "The client address street 1", required = true)
    private String streetDescOne;

    @Schema(description = "The client address street 2")
    private String streetDescTwo;

}
