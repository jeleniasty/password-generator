package com.jeleniasty.passwordgenerator.service;

import com.jeleniasty.passwordgenerator.entity.Password;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService{
    @Override
    public List<Password> getPasswords(int numberOfPasswords) {

        List<Password> passwords = new ArrayList<>();

        while (passwords.size() < numberOfPasswords) {
            int random = (int) (4 * Math.random());
            switch (random) {
                case 0 -> passwords.add(getWeakPassword());
                case 1 -> passwords.add(getMediumPassword());
                case 2 -> passwords.add(getStrongPassword());
                case 3 -> passwords.add(getUberStrongPassword());
            }
        }
        return passwords;
    }



    public Password getWeakPassword() {
        // słabe (długość do 5 znaków, bez znaków specjalnych, tylko małe litery lub duże litery)

        int numberOfCharacters = (int) (5 *  Math.random()) + 1;
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < numberOfCharacters) {

            int random = (int) (2 * Math.random());
            if (random == 0) {
                stringBuilder.append(getRandomLowerCaseLetter());
            } else {
                stringBuilder.append(getRandomUpperCaseLetter());
            }
        }

        return new Password(
                getTimestampDD_MM_YYYY_HH_MM_SS(),
                String.valueOf(stringBuilder),
                PasswordStrength.WEAK);
    }

    public Password getMediumPassword() {

//      średnie (długość powyżej 5 znaków, bez znaków specjalnych, małe lub duże litery)

        int numberOfCharacters =(int) (4 * Math.random()) + 5;
        StringBuilder stringBuilder = new StringBuilder();


        while (stringBuilder.length() < numberOfCharacters) {

            int random = (int) (2 * Math.random());
            if (random == 0) {
            stringBuilder.append(getRandomLowerCaseLetter());
            } else {
                stringBuilder.append(getRandomLowerCaseLetter());
            }
        }

        return new Password(
                getTimestampDD_MM_YYYY_HH_MM_SS(),
                String.valueOf(stringBuilder),
                PasswordStrength.MEDIUM);
    }

    public Password getStrongPassword() {

//      silne (długość powyżej 8 znaków, znaki specjalne, małe i duże litery)

        int numberOfCharacters = (int) (9 * Math.random()) + 8;
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < numberOfCharacters) {

            int random = (int) (3 * Math.random());
            if (random == 0) {
                stringBuilder.append(getRandomLowerCaseLetter());
            } else if (random == 1) {
                stringBuilder.append(getRandomLowerCaseLetter());
            } else {
                stringBuilder.append(getRandomSpecialCharacter());
            }
        }

        return new Password(
                getTimestampDD_MM_YYYY_HH_MM_SS(),
                String.valueOf(stringBuilder),
                PasswordStrength.STRONG);
    }

    public Password getUberStrongPassword() {

//      uber silne (długość powyżej 16 znaków, znaki specjalne, małe i duże litery)

        int numberOfCharacters = (int) (17 * Math.random()) + 16;
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < numberOfCharacters) {

            int random = (int) (3 * Math.random());
            if (random == 0) {
                stringBuilder.append(getRandomLowerCaseLetter());
            } else if (random == 1) {
                stringBuilder.append(getRandomLowerCaseLetter());
            } else {
                stringBuilder.append(getRandomSpecialCharacter());
            }
        }

        return new Password(
                getTimestampDD_MM_YYYY_HH_MM_SS(),
                String.valueOf(stringBuilder),
                PasswordStrength.UBER_STRONG);
    }

    private char getRandomLowerCaseLetter() {
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        int random = (int) (lowerCaseLetters.length() * Math.random());

        return lowerCaseLetters.charAt(random);
    }

    private char getRandomUpperCaseLetter() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int random = (int) (upperCaseLetters.length() * Math.random());

        return upperCaseLetters.charAt(random);
    }

    private char getRandomSpecialCharacter() {
        String specialCharacters = "~`!@#$%^&*()-_+={}[]|\\/:;\"'<>,.?";
        int random = (int) (specialCharacters.length() * Math.random());

        return specialCharacters.charAt(random);
    }

    private String getTimestampDD_MM_YYYY_HH_MM_SS() {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return timestamp.format(dateTimeFormatter);
    }

    public static void main(String[] args) {
    PasswordServiceImpl passwordService = new PasswordServiceImpl();
    Password password = passwordService.getStrongPassword();
        System.out.println(password.getDate());
        System.out.println(password.getPassword());
        System.out.println(password.getPasswordStrength());

    }

}
