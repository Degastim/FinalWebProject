package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.DrugOrder;
import com.epam.pharmacy.model.service.DrugOrderService;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DrugOrderServiceImplTest {
    private DrugOrderService drugOrderService;

    @BeforeTest
    public void setUp() {
        drugOrderService = DrugOrderServiceImpl.getInstance();
    }

    @Test
    public void testFindByCustomerId() {
        List<DrugOrder> expected = new ArrayList<>();
        List<DrugOrder> actual = new ArrayList<>();
        long customerId = 0;
        try {
            actual = drugOrderService.findByCustomerId(customerId);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindByStatus() {
        DrugOrder.Status status = DrugOrder.Status.PROCESSING;
        List<DrugOrder> expected = new ArrayList<>();
        List<DrugOrder> actual = new ArrayList<>();
        long customerId = 0;
        try {
            actual = drugOrderService.findByStatus(status);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }
}