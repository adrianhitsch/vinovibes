package com.vinovibes.vinoapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpDto(
    @NotBlank String email,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String password,
    @NotBlank String passwordRepeat,
    @NotNull Boolean eighteen,
    @NotNull Boolean privacy
) {}
