package com.epam.pharmacy.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {
    private static final Logger logger = LogManager.getLogger();
    private static final String ENCRYPTION_TYPE = "SHA-1";

    private Encrypter() {
    }

    public static String encrypt(String value) {
        MessageDigest messageDigest;
        byte[] bytesEncoded = null;
        try {
            messageDigest = MessageDigest.getInstance(ENCRYPTION_TYPE);
            messageDigest.update(value.getBytes(StandardCharsets.UTF_8));
            bytesEncoded = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.ERROR, "Value hasn't been encrypted");
        }
        BigInteger bigInt = new BigInteger(1, bytesEncoded);
        String resHex = bigInt.toString(16);
        logger.log(Level.DEBUG, "Value has been encrypted");
        return resHex;
    }
}
