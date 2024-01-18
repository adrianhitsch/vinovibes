package com.vinovibes.vinoapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record PasswordResetDto(@NotBlank String password, @NotBlank String passwordRepeat, @NotBlank String token) {}
