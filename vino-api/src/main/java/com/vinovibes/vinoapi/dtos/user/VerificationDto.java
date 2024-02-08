package com.vinovibes.vinoapi.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for verification.
 */
public record VerificationDto(
    @Schema(description = "User email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String email,
    @Schema(description = "One-time password", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    String otp
) {}
