package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.User;

import java.util.List;

public interface DrugOrderService {
    boolean orderPayment(User customer, Drug drug, int drugAmount) throws ServiceException;

    List<Order> findByCustomerId(long customerId) throws ServiceException;

    List<Order> findByStatus(Order.Status status) throws ServiceException;

    void updateStatusById(long drugOrderId, Order.Status status) throws ServiceException;

    boolean refuseDrugOrderService(long drugOrderId) throws ServiceException;
}
