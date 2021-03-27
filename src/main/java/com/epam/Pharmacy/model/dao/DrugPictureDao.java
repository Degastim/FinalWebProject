package com.epam.Pharmacy.model.dao;

import com.epam.Pharmacy.exception.DaoException;

public interface DrugPictureDao {
    void add(String drugPicture, int pictureDrugId) throws DaoException;
}
