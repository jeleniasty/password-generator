package com.jeleniasty.passwordgenerator.controller;

import com.jeleniasty.passwordgenerator.dto.PasswordDTO;
import com.jeleniasty.passwordgenerator.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("/passwords")
    public List<PasswordDTO> getPasswords(@RequestParam int number) {
        return passwordService.generatePasswords(number);
    }
}
