package com.vinovibes.vinoapi.dtos.errors;

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

    private final String status;
}
