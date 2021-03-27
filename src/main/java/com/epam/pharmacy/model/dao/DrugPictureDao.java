package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;

public interface DrugPictureDao {
    void add(String drugPicture, int pictureDrugId) throws DaoException;
}
