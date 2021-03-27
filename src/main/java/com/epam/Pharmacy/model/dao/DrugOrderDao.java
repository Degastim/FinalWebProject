package com.epam.Pharmacy.model.dao;

import com.epam.Pharmacy.exception.DaoException;

public interface DrugOrderDao {
    void add(long customerId, int drugId, int amount) throws DaoException;
}
