package com.vinovibes.vinoapi.dtos.rating;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a rating.
 */
public record CreateRatingDto(
    @NotNull Long wineId,
    @NotNull Double value,
    @NotNull String userComment,
    @NotNull String vintage,
    @NotNull Double price,
    @NotNull String priceType
) {}
