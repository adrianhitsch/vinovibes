package com.vinovibes.vinoapi.dtos.user;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for password reset.
 */
public record PasswordResetDto(@NotBlank String password, @NotBlank String passwordRepeat, @NotBlank String token) {}
