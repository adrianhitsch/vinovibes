package com.vinovibes.vinoapi.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for password reset.
 */
public record PasswordResetDto(
    @Schema(description = "New user password", example = "password", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String password,
    @Schema(
        description = "Repeat of the new user password",
        example = "password",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    String passwordRepeat,
    @Schema(
        description = "Password reset token",
        example = "TzS4vKyryD6DrbMZpcFJv0pJDm6iFJrf",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    String token
) {}
