package com.mboaeat.common.dto.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode(callSuper = true)
@Schema(description = "The class represents the subset of data returned from a menu when search for menus is conducted")
public class MenuSearchResult extends BaseSearchResult {

    @Schema(description = "The name of thr menu", required = true)
    private final String name;
    @Schema(description = "The category of menu", required = true)
    private final String category;
    @Schema(description = "The menu price", required = true)
    private final Double price;

    /**
     *
     * @param id The menu ID
     * @param name The name of menu
     * @param category The category of the menu
     * @param price The menu price
     */
    @JsonCreator
    public MenuSearchResult(@NotNull @JsonProperty("id") final Long id,
                            @NotNull @JsonProperty("name") final String name,
                            @NotNull @JsonProperty("category") final String category,
                            @NotNull @JsonProperty("price") final Double price){
        super(id.toString());
        this.name = name;
        this.category = category;
        this.price = price;
    }
}