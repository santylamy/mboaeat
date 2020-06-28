package com.mboaeat.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslateDto {
    private String reference;
    private String description;
}
