package com.vinovibes.vinoapi.dtos.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * DTO for user error.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class UserErrorDto extends ErrorDto {

    @Schema(description = "User status", example = "ACTIVE")
    private final String status;
}
