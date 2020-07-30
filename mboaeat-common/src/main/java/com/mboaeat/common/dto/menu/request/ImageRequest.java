package com.mboaeat.common.dto.menu.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mboaeat.common.dto.menu.MenuInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.File;

@Data
@Builder
@JsonDeserialize(builder = ImageRequest.ImageRequestBuilder.class)
@Schema(description = "The image(photo) for menu")
public class ImageRequest extends MenuInfoDTO {

    @NotNull
    @Schema(description = "An image file", required = true)
    private File file;

    @Schema(description = "The id image file in the cloud", required = true)
    private String idInCLoud;
}
