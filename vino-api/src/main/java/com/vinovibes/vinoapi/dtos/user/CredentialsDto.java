package com.vinovibes.vinoapi.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for credentials.
 */
public record CredentialsDto(
    @Schema(description = "User email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String email,
    @Schema(description = "User password", example = "password", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String password
) {}
