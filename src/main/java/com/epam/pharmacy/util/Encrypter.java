package com.epam.pharmacy.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Encryption string class.
 *
 * @author Yauheni Tsitou.
 */
public class Encrypter {

    /**
     * Logger for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    private Encrypter() {
    }

    /**
     * Method for encrypting strings.
     *
     * @param value Encryption value.
     * @return Encrypted string.
     */
    public static String encrypt(String value) {
        String salt = BCrypt.gensalt(10);
        String hashedValue = BCrypt.hashpw(value, salt);
        logger.log(Level.DEBUG, "Value has been encrypted");
        return hashedValue;
    }

    /**
     * Method for checking if the encValue encrypted version of a value is.
     *
     * @param value    Unencrypted string.
     * @param encValue encrypted string for comparison.
     * @return boolean, which indicates whether the encValue is encrypted from value.
     */
    public static boolean check(String value, String encValue) {
        boolean result = BCrypt.checkpw(value, encValue);
        logger.log(Level.DEBUG, "Value has been checked: " + result);
        return result;
    }
}
