package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.User;

public interface DrugOrderService {
    boolean orderPayment(User customer, Drug drug, int drugAmount) throws ServiceException;
}
