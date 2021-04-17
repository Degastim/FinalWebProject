package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.service.DrugService;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

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
        return new Object[][]{{1, 2}, {2,2}};
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
            fail(e.getMessage(), e);
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
        int actual;
        try {
            actual = drugService.countLastPaginationPage(currentPaginationPage);
            assertEquals(actual, expected);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
    }

    @Test
    public void testFindDrugIdByDrugNameAndDosage() {
        String drugName = "Аналгин";
        int drugDosage = 500;
        Optional<Integer> expected = Optional.of(2);
        Optional<Integer> actual;
        try {
            actual = drugService.findDrugIdByDrugNameAndDosage(drugName, drugDosage);
            assertEquals(actual, expected);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
    }

    @Test
    public void testFindDrugByDrugNameAndDosage() {
        long drugId = 2;
        String drugName = "Аналгин";
        String description = "После приема внутрь метамизол натрия быстро гидролизуется в желудочном соке с образованием активного метаболита 4-метил-амино-антипирина, который после всасывания метаболизируется в 4-формил-амино-антипирин и другие метаболиты.";
        boolean needPrescription = false;
        int drugDosage = 500;
        BigDecimal price = BigDecimal.valueOf(100);
        Drug drug = new Drug(drugId, drugName, 8, description, needPrescription, drugDosage, price);
        Optional<Drug> expected = Optional.of(drug);
        Optional<Drug> actual;
        try {
            actual = drugService.findDrugByDrugNameAndDosage("Аналгин", 500);
            if (actual.isEmpty()) {
                fail("No drug found in the database");
            }
            Drug drug1 = actual.get();
            drug1.setDrugPictureList(null);
            assertEquals(actual, expected);
        } catch (ServiceException e) {
            fail(e.getMessage(), e);
        }
    }
}