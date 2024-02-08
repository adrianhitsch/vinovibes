package com.vinovibes.vinoapi.handlers;

import com.vinovibes.vinoapi.dtos.errors.ErrorDto;
import com.vinovibes.vinoapi.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Exception handler for the application.
 */
@ControllerAdvice
public class AppExceptionHandler {

    /**
     * Handles the exception.
     * @param exception exception
     * @return response entity with error DTO
     */
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException exception) {
        setErrorMessageToErrorDto(exception);
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getErrorDto());
    }

    /**
     * Sets the error message to errorDTO.
     * @param e exception
     */
    private void setErrorMessageToErrorDto(AppException e) {
        e.getErrorDto().setMessage(e.getMessage());
    }
}
