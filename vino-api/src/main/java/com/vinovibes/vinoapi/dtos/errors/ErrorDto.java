package com.vinovibes.vinoapi.dtos.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for error.
 */
@Data
public class ErrorDto {

    @Schema(description = "Error message", example = "Wine not found")
    private String message;
}
