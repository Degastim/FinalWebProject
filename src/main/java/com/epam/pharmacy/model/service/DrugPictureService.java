package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;

public interface DrugPictureService {
    void add(String drugPicture, int pictureDrugId) throws ServiceException;
}
