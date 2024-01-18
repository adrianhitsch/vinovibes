package com.vinovibes.vinoapi.dtos;

import jakarta.validation.constraints.NotBlank;

public record EmailDto(@NotBlank String email) {}
