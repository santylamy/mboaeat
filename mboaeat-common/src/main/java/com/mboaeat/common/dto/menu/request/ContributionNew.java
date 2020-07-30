package com.mboaeat.common.dto.menu.request;

import com.mboaeat.common.dto.menu.MenuInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mboaeat.domain.CollectionsUtils.*;

@Data
@Builder
@Schema(description = "An object representing a new contribution")
public class ContributionNew<T extends MenuInfoDTO> {

    @Schema(description = "Elements to be added <A new element>")
    @Valid
    @Builder.Default
    private List<T> elementsToAdd = newArrayList();

    @Schema(description = "Elements to be updated <The ID of the updated element, A new element>")
    @Valid
    @Builder.Default
    private Map<Long, T> elementsToUpdate = newHashMap();

    @Schema(description = "Element IDs to be deleted <The ID of the element to be deleted>")
    @Builder.Default
    private Set<Long> idsToDelete = newHashSet();
}
