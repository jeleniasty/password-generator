package com.jeleniasty.passwordgenerator.service;

import com.jeleniasty.passwordgenerator.dto.PasswordDTO;

import java.util.List;

public interface PasswordService {
    List<PasswordDTO> generatePasswords(int numberOfPasswords);

    PasswordDTO checkPassword(String password);

    PasswordDTO deletePassword(String password);
}
