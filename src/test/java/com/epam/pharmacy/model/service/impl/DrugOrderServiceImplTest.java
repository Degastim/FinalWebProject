package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.DrugOrder;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugOrderService;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
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
        User customer = new User(2, "Евгений", "Титов", "cool.cool-superz@yandex.by", User.Role.CUSTOMER, new BigDecimal("110.00"));
        Drug drug = new Drug(2, "Аналгин", 8, "После приема внутрь метамизол натрия быстро гидролизуется в желудочном соке с образованием активного метаболита 4-метил-амино-антипирина, который после всасывания метаболизируется в 4-формил-амино-антипирин и другие метаболиты.", false, 500, new BigDecimal("100.00"));
        DrugOrder drugOrder = new DrugOrder(2, customer, drug, 1, DrugOrder.Status.PROCESSING);
        expected.add(drugOrder);
        List<DrugOrder> actual = new ArrayList<>();
        long customerId = 2;
        try {
            actual = drugOrderService.findByCustomerId(customerId);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testFindByStatus() {
        List<DrugOrder> expected = new ArrayList<>();
        User customer = new User(2, "Евгений", "Титов", "cool.cool-superz@yandex.by", User.Role.CUSTOMER, new BigDecimal("110.00"));
        Drug drug = new Drug(2, "Аналгин", 8, "После приема внутрь метамизол натрия быстро гидролизуется в желудочном соке с образованием активного метаболита 4-метил-амино-антипирина, который после всасывания метаболизируется в 4-формил-амино-антипирин и другие метаболиты.", false, 500, new BigDecimal("100.00"));
        DrugOrder drugOrder = new DrugOrder(2, customer, drug, 1, DrugOrder.Status.PROCESSING);
        expected.add(drugOrder);
        List<DrugOrder> actual = new ArrayList<>();
        try {
            actual = drugOrderService.findByStatus(DrugOrder.Status.PROCESSING);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage(), e);
        }
        Assert.assertEquals(actual, expected);
    }
}