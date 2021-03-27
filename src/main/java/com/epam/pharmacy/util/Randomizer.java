package com.epam.pharmacy.util;

public class Randomizer {
    public static int random(int numberDigits) {
        int result = (int) (Math.random() * Math.pow(10, numberDigits));
        return result;
    }
}
