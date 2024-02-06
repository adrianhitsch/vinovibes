package com.vinovibes.vinoapi.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record EmailDto(@NotBlank String email) {}
