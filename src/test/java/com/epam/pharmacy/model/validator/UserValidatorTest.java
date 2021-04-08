package com.epam.pharmacy.model.validator;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class UserValidatorTest {

    @DataProvider(name = "passwords")
    public Object[][] createPasswordData() {
        return new Object[][]{{"Qwerty#123", true}, {"<script>alert(Alarm)</script>", false}, {"<input></input>", false}, {"qwerty", false}};
    }

    @DataProvider(name = "surnames")
    public Object[][] createSurnameData() {
        return new Object[][]{{"Евгений", true}, {"Yauheni", true}, {"<input></input>", false}, {"<script>alert(Alarm)</script>", false}, {"qwer", false}};
    }

    @DataProvider(name = "names")
    public Object[][] createNameData() {
        return new Object[][]{{"Евгений", true}, {"Yauheni", true}, {"<input></input>", false}, {"<script>alert(Alarm)</script>", false}, {"qwer", false}};
    }

    @DataProvider(name = "emails")
    public Object[][] createEmailData() {
        return new Object[][]{{"zhenya", false}, {"евгений@yandex.by", false}, {"yandex@gmail.com", true}, {"<script>alert(Alarm)</script>", false}, {"qwer", false}};
    }

    @Test(dataProvider = "passwords")
    public void testIsPasswordValid(String password, boolean expected) {
        boolean actual = UserValidator.isPasswordValid(password);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "surnames")
    public void testIsSurnameValid(String surname, boolean expected) {
        boolean actual = UserValidator.isSurnameValid(surname);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "names")
    public void testIsNameValid(String name, boolean expected) {
        boolean actual = UserValidator.isSurnameValid(name);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "emails")
    public void testIsEmailValid(String email, boolean expected) {
        boolean actual = UserValidator.isEmailValid(email);
        assertEquals(actual, expected);
    }
}