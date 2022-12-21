package com.jeleniasty.passwordgenerator.service;

import com.jeleniasty.passwordgenerator.dto.PasswordDTO;
import com.jeleniasty.passwordgenerator.repository.Password;

import java.util.List;

public interface PasswordService {

    List<PasswordDTO> generateSpecifiedPasswords(int passwordLength,
                                                 boolean hasSpecialCharacters,
                                                 boolean hasLowerCaseLetters,
                                                 boolean hasUpperCaseLetters,
                                                 int numberOfPasswords);
    List<PasswordDTO> generateRandomPasswords(int numberOfPasswords);

    List<Password> checkPassword(String password);

    void deletePassword(String password);
}
