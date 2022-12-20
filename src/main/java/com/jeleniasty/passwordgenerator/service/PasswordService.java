package com.jeleniasty.passwordgenerator.service;

import com.jeleniasty.passwordgenerator.PasswordGeneratorApplication;
import com.jeleniasty.passwordgenerator.entity.Password;

import java.util.List;

public interface PasswordService {
    List<Password> getPasswords(int numberOfPasswords);
}
