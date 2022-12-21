package com.jeleniasty.passwordgenerator.service;

import com.jeleniasty.passwordgenerator.dto.Source;
import com.jeleniasty.passwordgenerator.repository.Password;
import com.jeleniasty.passwordgenerator.repository.PasswordRepository;
import com.jeleniasty.passwordgenerator.dto.PasswordDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final PasswordRepository passwordRepository;
    private final AESEncryptor passwordEncryptor;
    private static final String KEY = "4ca9d128-41b4-4688-be03-fb3fb3aa5efb";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SPECIAL_CHARACTERS = "~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";

    @Override
    public List<PasswordDTO> generateSpecifiedPasswords(int passwordLength,
                                                        boolean hasSpecialCharacters,
                                                        boolean hasLowerCaseLetters,
                                                        boolean hasUpperCaseLetters,
                                                        int numberOfPasswords) {

        List<PasswordDTO> passwords = new ArrayList<>();

        while (passwords.size() < numberOfPasswords) {
            String password;

            if (hasSpecialCharacters) {
                password = generatePassword(passwordLength, LOWERCASE_LETTERS+UPPERCASE_LETTERS+SPECIAL_CHARACTERS);
            } else if (hasLowerCaseLetters && hasUpperCaseLetters) {
                password = generatePassword(passwordLength, LOWERCASE_LETTERS + UPPERCASE_LETTERS);
            } else if (!hasLowerCaseLetters) {
                password = generatePassword(passwordLength, UPPERCASE_LETTERS);
            } else {
                password = generatePassword(passwordLength, LOWERCASE_LETTERS);
            }
            passwords.add(new PasswordDTO(
                    getTimestamp(),
                    password,
                    checkPasswordStrength(password),
                    getPasswordSource(password)));
        }

        savePasswords(passwords);

        return passwords;
    }

    @Override
    public List<PasswordDTO> generateRandomPasswords(int numberOfPasswords) {

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

        savePasswords(passwords);

        return passwords;
    }

    private void savePasswords(List<PasswordDTO> passwords) {

        passwordRepository.saveAll(passwords.stream()
                .filter(password -> password.getSource() == Source.GENERATED)
                .map(passwordDTO -> new Password(
                        passwordDTO.getDate(),
                        passwordEncryptor.encrypt(passwordDTO.getPassword(), KEY),
                        passwordDTO.getPasswordStrength()))
                .collect(Collectors.toList()));
    }

    @Override
    public Password checkPassword(String password) {
        if (passwordRepository.findByPassword(passwordEncryptor.encrypt(password, KEY)) == null) {
            return new Password(
                    password,
                    checkPasswordStrength(password));
        }
        return passwordRepository.findByPassword(passwordEncryptor.encrypt(password, KEY));
    }

    @Override
    @Transactional
    public void deletePassword(String password) {

        passwordRepository.deleteByPassword(passwordEncryptor.encrypt(password, KEY));
    }

    private PasswordDTO getWeakPassword() {
        int numberOfCharacters = getRandomNumberFromRange(1, 5);
        String password = generatePassword(numberOfCharacters, LOWERCASE_LETTERS + UPPERCASE_LETTERS);

        return new PasswordDTO(
                getTimestamp(),
                password,
                PasswordStrength.WEAK,
                getPasswordSource(password));
    }

    private PasswordDTO getMediumPassword() {
        int numberOfCharacters = getRandomNumberFromRange(5, 8);
        String password = generatePassword(numberOfCharacters, LOWERCASE_LETTERS + UPPERCASE_LETTERS);

        return new PasswordDTO(
                getTimestamp(),
                password,
                PasswordStrength.MEDIUM,
                getPasswordSource(password));
    }

    private PasswordDTO getStrongPassword() {
        int numberOfCharacters = getRandomNumberFromRange(8, 16);
        String password = generatePassword(numberOfCharacters, LOWERCASE_LETTERS + UPPERCASE_LETTERS + SPECIAL_CHARACTERS);

        return new PasswordDTO(
                getTimestamp(),
                password,
                PasswordStrength.STRONG,
                getPasswordSource(password));
    }

    private PasswordDTO getUberStrongPassword() {
        int numberOfCharacters = getRandomNumberFromRange(16, 32);
        String password = generatePassword(numberOfCharacters, LOWERCASE_LETTERS + UPPERCASE_LETTERS + SPECIAL_CHARACTERS);
        return new PasswordDTO(
                getTimestamp(),
                password,
                PasswordStrength.UBER_STRONG,
                getPasswordSource(password));
    }

    private Source getPasswordSource(String password) {
        Password passwordFound = passwordRepository.findByPassword(passwordEncryptor.encrypt(password, KEY));
        return passwordFound == null ? Source.GENERATED : Source.DATABASE;
    }

    private String generatePassword(int numberOfCharacters, String characters) {
        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() < numberOfCharacters) {
            stringBuilder.append(getRandomCharacterFromString(characters));
        }
        return stringBuilder.toString();
    }

    private PasswordStrength checkPasswordStrength(String password) {
        if (containsSpecialCharacter(password)) {
            if (password.length() > 16) {
                return PasswordStrength.UBER_STRONG;
            }
            return PasswordStrength.STRONG;
        } else if (password.length() > 5) {
            return PasswordStrength.MEDIUM;
        } else {
            return PasswordStrength.WEAK;
        }
    }

    private boolean containsSpecialCharacter(String password) {
        String regex = "^(?=.*[~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]).+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private char getRandomCharacterFromString(String passwordLetters) {
        return passwordLetters.charAt(getRandomNumberToLimit(passwordLetters.length()));
    }

    private static int getRandomNumberToLimit(int limit) {
        return (int) (limit * Math.random());
    }

    private int getRandomNumberFromRange(int minRange, int maxRange) {
        return (int) ((maxRange - minRange + 1) + minRange * Math.random());
    }

    private LocalDateTime getTimestamp() {
        return LocalDateTime.now();
    }


}
