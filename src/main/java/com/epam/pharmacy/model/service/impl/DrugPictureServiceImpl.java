package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugPictureDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.entity.DrugPicture;
import com.epam.pharmacy.model.service.DrugPictureService;

/**
 * Class-service for working with {@DrugPicture}.
 * @see DrugPicture
 * @author Yauheni Tsitou.
 */
public class DrugPictureServiceImpl implements DrugPictureService {

    /**
     * Reference to an object of class {@code DrugPictureServiceImpl}.
     */
    private static final DrugPictureService instance = new DrugPictureServiceImpl();

    private DrugPictureServiceImpl() {
    }

    /**
     * Method that returns a reference to an object
     *
     * @return Reference to an object of class {@code DrugPictureServiceImpl}.
     */
    public static DrugPictureService getInstance() {
        return instance;
    }

    /**
     * Reference to an object of class {@code DrugPictureDao}.
     */
    private static final DrugPictureDao drugPictureDao = DrugPictureDao.getInstance();

    @Override
    public void add(String drugPictureString, long pictureDrugId) throws ServiceException {
        if (!drugPictureString.isBlank()) {
            EntityTransaction transaction = new EntityTransaction();
            transaction.init(drugPictureDao);
            try {
                DrugPicture drugPicture = new DrugPicture(pictureDrugId, drugPictureString);
                drugPictureDao.add(drugPicture);
            } catch (DaoException e) {
                throw new ServiceException(e);
            } finally {
                transaction.end();
            }
        }
    }

    @Override
    public void delete(long drugPictureId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(drugPictureDao);
        try {
            drugPictureDao.delete(drugPictureId);
            drugPictureDao.changeAutoincrement(drugPictureId);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }
}
