package com.epam.Pharmacy.util;

import java.util.Base64;

public class Base64Encoder {
    public static byte[] encode(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] result = encoder.encode(bytes);
        return result;
    }
}
