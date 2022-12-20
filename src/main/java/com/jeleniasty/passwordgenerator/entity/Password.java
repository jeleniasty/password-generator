package com.jeleniasty.passwordgenerator.entity;

import com.jeleniasty.passwordgenerator.service.PasswordStrength;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Password {
    private String date;
    private String password;
    private PasswordStrength passwordStrength;
}
