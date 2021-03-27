package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugPictureDao;
import com.epam.pharmacy.model.dao.impl.DrugPictureDaoImpl;
import com.epam.pharmacy.model.service.DrugPictureService;

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
