package com.vinovibes.vinoapi.handlers;

import com.vinovibes.vinoapi.dtos.ErrorDto;
import com.vinovibes.vinoapi.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorDto(e.getMessage()));
    }
}
