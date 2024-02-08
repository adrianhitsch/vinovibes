package com.vinovibes.vinoapi.dtos.rating;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a rating.
 */
public record CreateRatingDto(
    @Schema(description = "Wine ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Long wineId,
    @Schema(description = "Rating value", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    Double value,
    @Schema(description = "User comment", example = "Great wine!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    String userComment,
    @Schema(description = "Wine vintage", example = "2015", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    String vintage,
    @Schema(description = "Wine price", example = "100.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    Double price,
    @Schema(description = "Wine price type", example = "RESTAURANT", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    String priceType
) {}
