package com.vinovibes.vinoapi.exceptions;

import com.vinovibes.vinoapi.dtos.errors.ErrorDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class for application.
 */
@Getter
public class AppException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorDto errorDto;

    public AppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorDto = new ErrorDto();
    }

    public AppException(String message, HttpStatus httpStatus, ErrorDto errorDto) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorDto = errorDto;
    }
}
