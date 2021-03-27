package com.epam.Pharmacy.model.service;

import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Drug;
import com.epam.Pharmacy.model.entity.User;

public interface DrugOrderService {
    boolean orderPayment(User customer, Drug drug, int drugAmount) throws ServiceException;
}
