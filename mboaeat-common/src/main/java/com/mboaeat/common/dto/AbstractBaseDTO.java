package com.mboaeat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Tag(name = "The base object")
public abstract class AbstractBaseDTO implements Serializable {

    @NotBlank
    @Schema(description = "The ID", required = true)
    private String id;


    public Long asLongId(){
        return Long.valueOf(id);
    }

}
