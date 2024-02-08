package com.vinovibes.vinoapi.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for signing up.
 */
public record SignUpDto(
    @NotBlank String email,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String password,
    @NotBlank String passwordRepeat,
    @NotNull Boolean eighteen,
    @NotNull Boolean privacy
) {}
