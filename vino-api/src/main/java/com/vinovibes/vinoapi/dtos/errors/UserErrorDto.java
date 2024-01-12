package com.vinovibes.vinoapi.dtos.errors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class UserErrorDto extends ErrorDto {

    private final String status;
}
