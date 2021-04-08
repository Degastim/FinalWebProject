package com.epam.pharmacy.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class Encrypter {
    private static final Logger logger = LogManager.getLogger();

    private Encrypter() {
    }

    public static String encrypt(String value) {
        String salt = BCrypt.gensalt(10);
        String hashedValue = BCrypt.hashpw(value, salt);
        logger.log(Level.DEBUG, "Value has been encrypted");
        return hashedValue;
    }
    public static boolean check(String value, String encValue) {
        boolean result = BCrypt.checkpw(value, encValue);
        logger.log(Level.DEBUG, "Value has been checked: " + result);
        return result;
    }
}
