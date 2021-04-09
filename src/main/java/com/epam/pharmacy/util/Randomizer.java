package com.epam.pharmacy.util;

/**
 * Class that creates random numbers.
 *
 * @author Yauheni Tsitou.
 */
public class Randomizer {
    /**
     * @param numberDigits Number indicating the number of digits in a number.
     * @return number with the required number of numbers.
     */
    public static int random(int numberDigits) {
        int result = (int) (Math.random() * Math.pow(10, numberDigits));
        return result;
    }
}
