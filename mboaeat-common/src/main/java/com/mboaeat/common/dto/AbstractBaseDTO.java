package com.mboaeat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", doNotUseGetters = true)
@Tag(name = "The base object")
public abstract class AbstractBaseDTO implements Serializable {

    @NotBlank
    @Schema(description = "The ID", required = true)
    private String id;


    public Long asLongId(){
        return Long.valueOf(id);
    }

}
