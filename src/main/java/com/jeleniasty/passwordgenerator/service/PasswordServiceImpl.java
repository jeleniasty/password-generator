package com.jeleniasty.passwordgenerator.service;

import com.jeleniasty.passwordgenerator.repository.Password;
import com.jeleniasty.passwordgenerator.repository.PasswordRepository;
import com.jeleniasty.passwordgenerator.dto.PasswordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService{
    private final PasswordRepository passwordRepository;

    private final PasswordEncoder passwordEncoder;

    public void save(PasswordDTO passwordDTO) {
        passwordRepository.save(new Password(
                passwordDTO.getDate(),
                passwordDTO.getPassword(),
                passwordDTO.getPasswordStrength()));
    }
    public Password findById(long id) {
        Optional<Password> result = passwordRepository.findById(id);
        Password passwordDAO = null;
        if (result.isPresent()) {
            passwordDAO = result.get();
        } else {
            throw new RuntimeException("Did not find employee id");
        }
        return passwordDAO;
    }
    @Override
    public List<PasswordDTO> generatePasswords(int numberOfPasswords) {

        List<PasswordDTO> passwords = new ArrayList<>();

        while (passwords.size() < numberOfPasswords) {
            int random = (int) (4 * Math.random());
            switch (random) {
                case 0 -> passwords.add(getWeakPassword());
                case 1 -> passwords.add(getMediumPassword());
                case 2 -> passwords.add(getStrongPassword());
                case 3 -> passwords.add(getUberStrongPassword());
            }
        }
        passwordRepository.saveAll(passwords.stream()
                .map(passwordDTO -> new Password(
                        passwordDTO.getDate(),
                        passwordEncoder.encode(passwordDTO.getPassword()),
                        passwordDTO.getPasswordStrength()))
                .collect(Collectors.toList()));

        return passwords;
    }

    @Override
    public PasswordDTO checkPassword(String password) {
        return null;
    }

    @Override
    public PasswordDTO deletePassword(String password) {
        return null;
    }

    private PasswordDTO getWeakPassword() {
        // słabe (długość do 5 znaków, bez znaków specjalnych, tylko małe litery lub duże litery)

        int numberOfCharacters = getRandomNumberFromRange(1,5);
        String password = getPasswordWithoutSpecialChars(numberOfCharacters);

        return new PasswordDTO(
                getTimestamp(),
                password,
                PasswordStrength.WEAK);
    }

    private PasswordDTO getMediumPassword() {

//      średnie (długość powyżej 5 znaków, bez znaków specjalnych, małe lub duże litery)

        int numberOfCharacters =getRandomNumberFromRange(5,8);
        String password = getPasswordWithoutSpecialChars(numberOfCharacters);

        return new PasswordDTO(
                getTimestamp(),
                password,
                PasswordStrength.MEDIUM);
    }

    private String getPasswordWithoutSpecialChars(int numberOfCharacters) {
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < numberOfCharacters) {

            int random = getRandomNumberToLimit(2);
            if (random == 0) {
                stringBuilder.append(getRandomUpperCaseLetter());
            } else {
                stringBuilder.append(getRandomLowerCaseLetter());
            }
        }
        return stringBuilder.toString();
    }

    private PasswordDTO getStrongPassword() {

//      silne (długość powyżej 8 znaków, znaki specjalne, małe i duże litery)

        int numberOfCharacters = getRandomNumberFromRange(8,16);
        String password = getPasswordWithSpecialChars(numberOfCharacters);

        return new PasswordDTO(
                getTimestamp(),
                password,
                PasswordStrength.STRONG);
    }

    private PasswordDTO getUberStrongPassword() {

//      uber silne (długość powyżej 16 znaków, znaki specjalne, małe i duże litery)

        int numberOfCharacters = getRandomNumberFromRange(16,32);
        String password = getPasswordWithSpecialChars(numberOfCharacters);
        return new PasswordDTO(
                getTimestamp(),
                password,
                PasswordStrength.UBER_STRONG);
    }

    private String getPasswordWithSpecialChars(int numberOfCharacters) {
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < numberOfCharacters) {

            int random = getRandomNumberToLimit(3);
            if (random == 0) {
                stringBuilder.append(getRandomUpperCaseLetter());
            } else if (random == 1) {
                stringBuilder.append(getRandomLowerCaseLetter());
            } else {
                stringBuilder.append(getRandomSpecialCharacter());
            }
        }
        return stringBuilder.toString();
    }

    private char getRandomLowerCaseLetter() {
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        return getRandomCharacterFromString(lowerCaseLetters);
    }

    private char getRandomUpperCaseLetter() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return getRandomCharacterFromString(upperCaseLetters);
    }

    private char getRandomSpecialCharacter() {
        String specialCharacters = "~`!@#$%^&*()-_+={}[]|\\/:;\"'<>,.?";
        return getRandomCharacterFromString(specialCharacters);
    }

    private char getRandomCharacterFromString(String passwordLetters) {
        return passwordLetters.charAt(getRandomNumberToLimit(passwordLetters.length()));
    }
    private int getRandomNumberToLimit(int limit) {
        return (int) (limit * Math.random());
    }

    private int getRandomNumberFromRange(int minRange, int maxRange) {
        return (int) ((maxRange - minRange + 1) + minRange * Math.random());
    }

    private LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }


}
