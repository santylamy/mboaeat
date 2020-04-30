package com.mboaeat.common.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Tag(name = "Message", description = "Error status message")
public class ErrorMessageDTO {

    @Schema(description = "Contains the same HTTP Status code returned by the server", required = true)
    private int status;

    @Schema(description = "Application specific error code", required = true)
    private String code;

    @Schema(description = "Message describing the error", required = true)
    private String message;
}
