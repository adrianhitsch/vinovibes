package com.vinovibes.vinoapi.handlers;

import com.vinovibes.vinoapi.dtos.errors.ErrorDto;
import com.vinovibes.vinoapi.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException exception) {
        setErrorMessageToErrorDto(exception);
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getErrorDto());
    }

    private void setErrorMessageToErrorDto(AppException e) {
        e.getErrorDto().setMessage(e.getMessage());
    }
}
