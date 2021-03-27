package com.epam.Pharmacy.model.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15}$";
    private static final String NAME_REGEX = "[a-zA-Zа-яА-Я_0-9-.]{5,45}";
    private static final String SURNAME_REGEX = "[a-zA-Zа-яА-Я_0-9-.]{5,50}";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        boolean result = matcher.matches();
        String log = result ? "Password is valid" : "Password isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }

    public static boolean isNameValid(String login) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(login);
        boolean result = matcher.matches();
        String log = result ? "Name is valid" : "Name isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }

    public static boolean isSurnameValid(String login) {
        Pattern pattern = Pattern.compile(SURNAME_REGEX);
        Matcher matcher = pattern.matcher(login);
        boolean result = matcher.matches();
        String log = result ? "Surname is valid" : "Surname isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        boolean result = matcher.matches();
        String log = result ? "Email is valid" : "Email isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }
}
