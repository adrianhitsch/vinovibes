package com.vinovibes.vinoapi.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for rating user.
 */
public record RatingUserDto(
    @Schema(description = "User ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) Long id,
    @Schema(description = "User name", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED) String name
) {}
