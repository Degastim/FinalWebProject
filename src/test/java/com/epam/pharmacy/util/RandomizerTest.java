package com.epam.pharmacy.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RandomizerTest {
    @Test
    public void testRandom() {
        int actual = 4;
        int number = Randomizer.random(4);
        String numberString = String.valueOf(number);
        int expected = numberString.length();
        assertEquals(actual, expected);
    }
}