package com.jeleniasty.passwordgenerator.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PasswordRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PasswordErrorResponse> handleException(PasswordNotFoundException exc) {

        PasswordErrorResponse error = new PasswordErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PasswordErrorResponse> handleException(Exception exc) {

        PasswordErrorResponse error = new PasswordErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
