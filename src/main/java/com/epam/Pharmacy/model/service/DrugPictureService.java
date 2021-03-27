package com.epam.Pharmacy.model.service;

import com.epam.Pharmacy.exception.ServiceException;

public interface DrugPictureService {
    void add(String drugPicture, int pictureDrugId) throws ServiceException;
}
