package com.vinovibes.vinoapi.dtos.wine;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for wine filter.
 */
public record WineFilterDto(
    @Schema(description = "Start index for pagination", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    int skip,
    @Schema(
        description = "Number of items to take for pagination",
        example = "10",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    int take,
    @Schema(
        description = "Sort category",
        example = "vintage",
        defaultValue = "id",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    String sortBy,
    @Schema(description = "Sort direction", example = "desc", defaultValue = "asc") @NotBlank String sortDirection,
    @Schema(description = "Wine category", example = "RED", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    String type
) {}
