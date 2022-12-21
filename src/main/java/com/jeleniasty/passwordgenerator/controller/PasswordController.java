package com.jeleniasty.passwordgenerator.controller;

import com.jeleniasty.passwordgenerator.dto.PasswordDTO;
import com.jeleniasty.passwordgenerator.repository.Password;
import com.jeleniasty.passwordgenerator.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("/passwords")
    public List<PasswordDTO> getRandomPasswords(@RequestParam int number) {
        return passwordService.generateRandomPasswords(number);
    }

    @PostMapping("/passwords/specify")
    public List<PasswordDTO> getSpecifiedPasswords(@RequestParam int passwordLength,
                                                   boolean hasSpecialChars,
                                                   boolean hasLowerCaseChars,
                                                   boolean hasUpperCaseChars,
                                                   int numberOfPasswords) {
        return passwordService.generateSpecifiedPasswords(
                        passwordLength,
                        hasSpecialChars,
                        hasLowerCaseChars,
                        hasUpperCaseChars,
                        numberOfPasswords);
    }

    @GetMapping("/passwords/check")
    private List<Password> checkPassword(@RequestBody String password) {
        return passwordService.checkPassword(password);
    }

    @DeleteMapping("/passwords/delete")
    private void deletePassword(@RequestBody String password) {
        passwordService.deletePassword(password);
    }
}
