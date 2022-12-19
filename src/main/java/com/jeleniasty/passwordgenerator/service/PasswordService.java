package com.jeleniasty.passwordgenerator.service;

import com.jeleniasty.passwordgenerator.entity.Password;

import java.util.List;

public interface PasswordService {
    List<String> getPasswords(int numberOfPasswords);
}
