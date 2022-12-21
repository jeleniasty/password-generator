package com.jeleniasty.passwordgenerator.dto;

import com.jeleniasty.passwordgenerator.service.PasswordStrength;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class PasswordDTO {
    private LocalDateTime date;
    private String password;
    private PasswordStrength passwordStrength;
    private Source source;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordDTO that = (PasswordDTO) o;
        return password.equals(that.password) && passwordStrength == that.passwordStrength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, passwordStrength);
    }
}
