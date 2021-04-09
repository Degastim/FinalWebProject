package com.epam.pharmacy.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;

/**
 * Class Base64Encoder.
 *
 * @author Yauheni Tsitou.
 */
public class Base64Encoder {

    /**
     * Logger for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Byte array encoding method method base64.
     *
     * @param bytes array of bytes to encode.
     * @return encoded byte array.
     */
    public static byte[] encode(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] result = encoder.encode(bytes);
        logger.log(Level.DEBUG, "Array of bytes encoded");
        return result;
    }
}
