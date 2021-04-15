package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.PrescriptionService;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrescriptionServiceImplTest {
    private PrescriptionService prescriptionService;

    @BeforeMethod
    public void setUp() {
        prescriptionService = PrescriptionServiceImpl.getInstance();
    }

    @Test
    public void testCheckPrescription() {
        boolean expected = false;
        Drug drug = new Drug(1);
        drug.setNeedPrescription(true);
        int drugAmount = 1;
        long customerId = 0;
        boolean actual = false;
        try {
            actual = prescriptionService.checkPrescription(customerId, drug, drugAmount);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindAllByCustomerId() {
        long customerId = 2;
        User customer = new User(customerId, "Евгений", "Титов", "cool.cool-superz@yandex.by", User.Role.CUSTOMER, new BigDecimal("110.00"));
        User doctor = new User(3, "Павел", "Алеутов", "zhenya.titov1@gmail.com", User.Role.DOCTOR, new BigDecimal("0"));
        Drug drug = new Drug(2, "Аналгин", 8, "После приема внутрь метамизол натрия быстро гидролизуется в желудочном соке с образованием активного метаболита 4-метил-амино-антипирина, который после всасывания метаболизируется в 4-формил-амино-антипирин и другие метаболиты.", false, 500, new BigDecimal("100.00"));
        Prescription prescription = new Prescription(3, customer, doctor, drug, 10, null, null, Prescription.Status.PROCESSING);
        List<Prescription> actual = new ArrayList<>();
        List<Prescription> expected = new ArrayList<>();
        expected.add(prescription);
        try {
            actual = prescriptionService.findAllByCustomerId(customerId);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindAllByDoctorIdAndStatus() {
        long doctorId = 3;
        User customer = new User(2, "Евгений", "Титов", "cool.cool-superz@yandex.by", User.Role.CUSTOMER, new BigDecimal("110.00"));
        User doctor = new User(3, "Павел", "Алеутов", "zhenya.titov1@gmail.com", User.Role.DOCTOR, new BigDecimal("0"));
        Drug drug = new Drug(2, "Аналгин", 8, "После приема внутрь метамизол натрия быстро гидролизуется в желудочном соке с образованием активного метаболита 4-метил-амино-антипирина, который после всасывания метаболизируется в 4-формил-амино-антипирин и другие метаболиты.", false, 500, new BigDecimal("100.00"));
        Prescription prescription = new Prescription(3, customer, doctor, drug, 10, null, null, Prescription.Status.PROCESSING);
        List<Prescription> actual = new ArrayList<>();
        List<Prescription> expected = new ArrayList<>();
        expected.add(prescription);
        try {
            actual = prescriptionService.findAllByDoctorIdAndStatus(doctorId, Prescription.Status.PROCESSING);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindPrescriptionById() {
        User customer = new User(2, "Евгений", "Титов", "cool.cool-superz@yandex.by", User.Role.CUSTOMER, new BigDecimal("110.00"));
        User doctor = new User(3, "Павел", "Алеутов", "zhenya.titov1@gmail.com", User.Role.DOCTOR, new BigDecimal("0"));
        Drug drug = new Drug(2, "Аналгин", 8, "После приема внутрь метамизол натрия быстро гидролизуется в желудочном соке с образованием активного метаболита 4-метил-амино-антипирина, который после всасывания метаболизируется в 4-формил-амино-антипирин и другие метаболиты.", false, 500, new BigDecimal("100.00"));
        Prescription prescription = new Prescription(3, customer, doctor, drug, 10, null, null, Prescription.Status.PROCESSING);
        Optional<Prescription> actual = Optional.empty();
        Optional<Prescription> expected = Optional.of(prescription);
        try {
            actual = prescriptionService.findPrescriptionById(3);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }
}