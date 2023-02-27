package com.seb.mobilebankapi.exception;

import org.springframework.http.HttpStatus;

public record ErrorMessage(int status, String message) {

    ErrorMessage(String message) {
        this(HttpStatus.BAD_REQUEST.value(), message);
    }
}
