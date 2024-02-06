package com.vinovibes.vinoapi.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record VerificationDto(@NotBlank String email, @NotBlank String otp) {}
