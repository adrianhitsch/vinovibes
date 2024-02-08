package com.vinovibes.vinoapi.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for signing up.
 */
public record SignUpDto(
    @Schema(description = "User email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String email,
    @Schema(description = "User first name", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String firstName,
    @Schema(description = "User last name", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String lastName,
    @Schema(description = "User password", example = "password", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String password,
    @Schema(
        description = "Repeat of the user password",
        example = "password",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    String passwordRepeat,
    @Schema(
        description = "Flag for user being 18 years old",
        example = "true",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull
    Boolean eighteen,
    @Schema(description = "Flag for user privacy", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    Boolean privacy
) {}
