package com.jeleniasty.passwordgenerator.controller;

import com.jeleniasty.passwordgenerator.entity.Password;
import com.jeleniasty.passwordgenerator.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @GetMapping("/api/passwords")
    public List<Password> getPasswords(@RequestParam int number) {
        return passwordService.getPasswords(number);
    }
}
