package com.vinovibes.vinoapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record CredentialsDto(@NotBlank String email, @NotBlank String password) {}
