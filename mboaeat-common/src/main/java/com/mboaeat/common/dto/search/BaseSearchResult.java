package com.mboaeat.common.dto.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@EqualsAndHashCode(of = "id")
@Schema(description = "The base object for search results")
public class BaseSearchResult implements Serializable {

    @Schema(description = "The Id", required = true)
    private final String id;

    @JsonCreator
    public BaseSearchResult(@NotNull @JsonProperty("id") final String id){
        this.id = id;
    }
}
