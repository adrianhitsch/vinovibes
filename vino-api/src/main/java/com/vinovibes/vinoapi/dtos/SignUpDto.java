package com.vinovibes.vinoapi.dtos;

public record SignUpDto(
    String email,
    String firstName,
    String lastName,
    String password,
    String passwordRepeat,
    Boolean eighteen,
    Boolean privacy
) {}
