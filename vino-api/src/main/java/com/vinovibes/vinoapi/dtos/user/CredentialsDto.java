package com.vinovibes.vinoapi.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record CredentialsDto(@NotBlank String email, @NotBlank String password) {}
