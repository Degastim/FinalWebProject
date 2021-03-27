package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

public interface DrugOrderDao {
    void add(long customerId, int drugId, int amount) throws DaoException;
}
