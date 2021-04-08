package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.PrescriptionService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PrescriptionServiceImplTest {
    private PrescriptionService prescriptionService;

    @BeforeMethod
    public void setUp() {
        prescriptionService = PrescriptionServiceImpl.getInstance();
    }

    @Test
    public void testCheckPrescription() {
        boolean expected = false;
        long customerId = 0;
        String drugName = "";
        int dosage = 0;
        boolean actual = false;
        try {
            actual = prescriptionService.checkPrescription(customerId, drugName, dosage);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }
}