package com.epam.pharmacy.model.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator class for validating user fields.
 *
 * @author Yauheni Tsitou.
 */
public class UserValidator {

    /**
     * Logger for writing logs.
     */
    private static final Logger logger = LogManager.getLogger();

    private UserValidator() {
    }

    /**
     * Regular expression for password validation.
     */
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15}$";

    /**
     * Regular expression for username validation.
     */
    private static final String NAME_REGEX = "[a-zA-Zа-яА-Я]{5,45}";

    /**
     * Regular expression for user surname validation.
     */
    private static final String SURNAME_REGEX = "[a-zA-Zа-яА-Я]{5,50}";

    /**
     * Regular expression for email validation.
     */
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Method for password validation.
     *
     * @param password String containing password for validation.
     * @return String validation result.
     */
    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        boolean result = matcher.matches();
        String log = result ? "Password is valid" : "Password isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }

    /**
     * Method for username validation/
     *
     * @param name String containing username for validation.
     * @return String validation result.
     */
    public static boolean isNameValid(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        boolean result = matcher.matches();
        String log = result ? "Username is valid" : "Username isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }

    /**
     * Method for user surname validation
     *
     * @param surname String containing user surname for validation.
     * @return String validation result.
     */
    public static boolean isSurnameValid(String surname) {
        Pattern pattern = Pattern.compile(SURNAME_REGEX);
        Matcher matcher = pattern.matcher(surname);
        boolean result = matcher.matches();
        String log = result ? "User surname is valid" : "User surname isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }

    /**
     * Method for email validation.
     *
     * @param email String containing email for validation.
     * @return String validation result.
     */
    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        boolean result = matcher.matches();
        String log = result ? "Email is valid" : "Email isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }
}
