package com.mboaeat.common.dto.error;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@Tag(name = "Error API", description = "Error status message")
public class ApiErrorDTO {
    private HttpStatus status;
    private String message;
    private List<ErrorMessageDTO> errorMessages = new ArrayList<>();

    public ApiErrorDTO(HttpStatus status, String message, List<ErrorMessageDTO> errorMessages) {
        this.status = status;
        this.message = message;
        this.errorMessages = errorMessages;
    }

    public ApiErrorDTO(HttpStatus status, String message, ErrorMessageDTO errorMessages) {
        this.status = status;
        this.message = message;
        this.errorMessages = Arrays.asList(errorMessages);
    }
}
