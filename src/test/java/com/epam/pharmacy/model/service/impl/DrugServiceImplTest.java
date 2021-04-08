package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.DrugService;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DrugServiceImplTest {
    private DrugService drugService;

    @BeforeTest
    public void setUp() {
        drugService = DrugServiceImpl.getInstance();
    }

    @DataProvider(name = "startPaginationPages")
    public Object[][] createStartPaginationPageData() {
        return new Object[][]{{1, 1}, {2, 1}, {6, 5}};
    }

    @DataProvider(name = "lastPaginationPages")
    public Object[][] createLastPaginationPageData() {
        return new Object[][]{{1, 2}, {2, 3}, {6, 3}};
    }

    @Test
    public void testCountPaginationPageAmount() {
        int actual = 0;
        int expected = 3;
        try {
            actual = drugService.countPaginationPageAmount();
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindCurrentPaginationPage() {
        String paginationPage = "next";
        int currentPaginationPage = 1;
        int expected = 2;
        int actual = 0;
        try {
            actual = drugService.findCurrentPaginationPage(paginationPage, currentPaginationPage);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "startPaginationPages")
    public void testCountStartPaginationPage(int currentPaginationPage, int expected) {
        int actual = drugService.countStartPaginationPage(currentPaginationPage);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "lastPaginationPages")
    public void testCountLastPaginationPage(int currentPaginationPage, int expected) {
        int actual = drugService.countLastPaginationPage(currentPaginationPage, 3);
        assertEquals(actual, expected);
    }
}