package com.epam.pharmacy.model.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator class for validating drug fields.
 *
 * @author Yauheni Tsitou.
 */
public class DrugValidator {

    /**
     * Logger for writing logs.
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Regular expression for drug name validation.
     */
    private static final String NAME_REGEX = "[a-zA-Zа-яА-Я]{5,45}";

    /**
     * Regular expression for drug description validation.
     */
    private static final String DESCRIPTION_XSS_REGEX = "</?script>";

    /**
     * Method for drug name validation
     *
     * @param name String containing drug name for validation.
     * @return String validation result.
     */
    public static boolean isNameValid(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        boolean result = matcher.matches();
        String log = result ? "Drug name is valid" : "Drug name isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }

    /**
     * Method for drug name validation/
     *
     * @param description String containing drug description for validation.
     * @return String validation result.
     */
    public static boolean isDescriptionValid(String description) {
        Pattern pattern = Pattern.compile(DESCRIPTION_XSS_REGEX);
        Matcher matcher = pattern.matcher(description);
        boolean result = !matcher.find();
        String log = result ? "Drug description is valid" : "Drug description isn't valid";
        logger.log(Level.INFO, log);
        return result;
    }
}
