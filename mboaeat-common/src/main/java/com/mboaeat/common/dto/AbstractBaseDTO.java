package com.mboaeat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Schema(description = "The base object")
public abstract class AbstractBaseDTO implements Serializable {

    @Schema(description = "The ID")
    private String id;


    public Long asLongId(){
        return StringUtils.isNotBlank(id) ? Long.valueOf(id) : null;
    }

}
