package com.seb.mobilebankapi.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ErrorMessage exceptionHandler(IllegalArgumentException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ErrorMessage exceptionHandler(EntityNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    ErrorMessage exceptionHandler(UnsupportedOperationException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}
