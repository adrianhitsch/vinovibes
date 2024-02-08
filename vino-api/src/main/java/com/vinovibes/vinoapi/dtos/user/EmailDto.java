package com.vinovibes.vinoapi.dtos.user;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for email.
 */
public record EmailDto(@NotBlank String email) {}
