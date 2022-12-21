package com.jeleniasty.passwordgenerator.dto;

import com.jeleniasty.passwordgenerator.service.PasswordStrength;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PasswordDTO {
    private LocalDateTime date;
    private String password;
    private PasswordStrength passwordStrength;
}
