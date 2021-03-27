package com.epam.Pharmacy.model.service.impl;

import com.epam.Pharmacy.exception.DaoException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.dao.DrugPictureDao;
import com.epam.Pharmacy.model.dao.impl.DrugPictureDaoImpl;
import com.epam.Pharmacy.model.service.DrugPictureService;

public class DrugPictureServiceImpl implements DrugPictureService {
    private static final DrugPictureService instance = new DrugPictureServiceImpl();

    private DrugPictureServiceImpl() {
    }

    public static DrugPictureService getInstance() {
        return instance;
    }

    private static final DrugPictureDao drugPictureDao = DrugPictureDaoImpl.getInstance();

    @Override
    public void add(String drugPicture, int pictureDrugId) throws ServiceException {
        try {
            drugPictureDao.add(drugPicture, pictureDrugId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
