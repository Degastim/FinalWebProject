package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugPictureDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.entity.DrugPicture;
import com.epam.pharmacy.model.service.DrugPictureService;

/**
 * Class-service for working with {@link DrugPicture}.
 *
 * @author Yauheni Tsitou.
 */
public class DrugPictureServiceImpl implements DrugPictureService {

    /**
     * Reference to an object of class {@link DrugPictureServiceImpl}.
     */
    private static final DrugPictureService instance = new DrugPictureServiceImpl();

    private DrugPictureServiceImpl() {
    }

    /**
     * Method that returns a reference to an object
     *
     * @return Reference to an object of class {@link DrugPictureServiceImpl}.
     */
    public static DrugPictureService getInstance() {
        return instance;
    }

    @Override
    public void add(String drugPictureString, long pictureDrugId) throws ServiceException {
        DrugPictureDao drugPictureDao = new DrugPictureDao();
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
        DrugPictureDao drugPictureDao = new DrugPictureDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(drugPictureDao);
        try {
            drugPictureDao.delete(drugPictureId);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }
}
