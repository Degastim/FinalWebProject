package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.UserService;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class UserServiceImplTest {
    private UserService userService;

    @BeforeTest
    public void setUp() {
        userService = UserServiceImpl.getInstance();
    }

    @DataProvider(name = "formValues")
    public Object[][] createFormData() {
        String[] formValue1 = new String[]{"Mortarion", "Kingon", "Qwerty#132", "cooll@yandex.by"};
        String[] formValue2 = new String[]{"<input></input>", "Титов", "Qwerty#132", "cool@yandex.by"};
        String[] formValue3 = new String[]{"Евгений", "Титов", "Qwerty#132", "1"};
        return new Object[][]{{formValue1, true}, {formValue2, false}, {formValue3, false}};
    }

    @Test
    public void testFindByEmailAndPassword() {
        String name = "Mortarion";
        String surname = "Kingon";
        String email = "N23th53g@yandex.by";
        String password = "Qwerty#123";
        User.Role role = User.Role.PHARMACIST;
        User expectedUser = new User(1, name, surname, email, role, BigDecimal.ZERO);
        Optional<User> expected = Optional.of(expectedUser);
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.findByEmailAndPassword(email, password);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindByRole() {
        String name = "Mortarion";
        String surname = "Kingon";
        String email = "N23th53g@yandex.by";
        User.Role role = User.Role.PHARMACIST;
        User expected = new User(1, name, surname, email, role, BigDecimal.ZERO);
        User actual = null;
        try {
            actual = userService.findByRole(role).get(0);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "formValues")
    public void testCheckRegistrationForm(String[] values, boolean expected) {
        String name = values[0];
        String surname = values[1];
        String password = values[2];
        String email = values[3];
        boolean actual = false;
        try {
            actual = userService.checkRegistrationForm(name, surname, password, email);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindById() {
        long id = 1;
        String name = "Mortarion";
        String surname = "Kingon";
        String email = "N23th53g@yandex.by";
        User.Role role = User.Role.PHARMACIST;
        User expectedUser = new User(1, name, surname, email, role, BigDecimal.ZERO);
        Optional<User> expected = Optional.of(expectedUser);
        Optional<User> actual = Optional.empty();
        try {
            actual = userService.findById(id);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }
}