package com.vinovibes.vinoapi.dtos.wine;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WineFilterDto(
    @NotBlank int skip,
    @NotBlank int take,
    @NotBlank String sortBy,
    @NotBlank String sortDirection,
    @NotNull String type
) {}
