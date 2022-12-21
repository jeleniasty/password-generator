package com.jeleniasty.passwordgenerator.service;

public class PasswordNotFoundException extends RuntimeException{
    public PasswordNotFoundException(String message) {
        super(message);
    }

}
