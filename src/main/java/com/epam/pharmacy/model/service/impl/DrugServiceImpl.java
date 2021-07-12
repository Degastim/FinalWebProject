package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugDao;
import com.epam.pharmacy.model.dao.DrugOrderDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.PrescriptionDao;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.service.DrugService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class-service for working with {@link Drug}.
 *
 * @author Yauheni Tsitou.
 * @see Drug
 */
public class DrugServiceImpl implements DrugService {

    /**
     * Reference to an object of class {@link DrugServiceImpl}.
     */
    private static final DrugService instance = new DrugServiceImpl();

    private DrugServiceImpl() {
    }

    /**
     * Method that returns a reference to an object.
     *
     * @return Reference to an object of class {@code DrugPictureServiceImpl}.
     */
    public static DrugService getInstance() {
        return instance;
    }

    /**
     * Field containing the number of drugs on one main page.
     */
    private static final int DRUGS_NUMBER_PER_PAGE = 4;

    /**
     * A string containing the type of next pagination.
     */
    private static final String PAGINATION_PAGE_NEXT = "next";

    /**
     * A string containing the type of previous pagination.
     */
    private static final String PAGINATION_PAGE_PREVIOUS = "previous";

    /**
     * Number of links in pagination
     */
    private static final int NUMBER_PAGINATION_LINKS = 3;

    @Override
    public List<Drug> findAllDrugs() throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            List<Drug> result = drugDao.findAll();
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Drug> findByIdWithImages(long drugId) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            Optional<Drug> drug = drugDao.findById(drugId);
            return drug;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean updateDrug(int drugId, String drugName, int drugAmount, String description, boolean needPrescription, BigDecimal price, double dosage) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            Optional<Drug> drugDaoOptional = drugDao.findByNameAndDosage(drugName, dosage);
            if (drugDaoOptional.isPresent()) {
                Drug drug = drugDaoOptional.get();
                if (drug.getId() != drugId) {
                    return false;
                }
            }
            Drug drug = new Drug(drugId, drugName, drugAmount, description, needPrescription, dosage, price);
            drugDao.update(drug);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }


    public List<Drug> findPaginationDrugs(int currentPaginationPage) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        List<Drug> drugList = new ArrayList<>();
        int lastDrugNumberPerPage = DRUGS_NUMBER_PER_PAGE * currentPaginationPage;
        try {
            List<Drug> allDrugList = drugDao.findAll();
            int allDrugSize = allDrugList.size();
            int index = lastDrugNumberPerPage - 4;
            Drug drug;
            while (index < allDrugSize && index != lastDrugNumberPerPage) {
                drug = allDrugList.get(index);
                drugList.add(drug);
                index++;
            }
            return drugList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public void deleteById(long drugId) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        DrugOrderDao drugOrderDao = new DrugOrderDao();
        PrescriptionDao prescriptionDao = new PrescriptionDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(drugDao, drugOrderDao, prescriptionDao);
        try {
            drugOrderDao.deleteByDrugId(drugId);
            prescriptionDao.deleteByDrugId(drugId);
            drugDao.delete(drugId);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }

    @Override
    public boolean add(String drugName, int drugAmount, String drugDescription, boolean needPrescription, double dosage, BigDecimal price) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            drugName = drugName.trim();
            boolean exist = drugDao.existByDrugNameAndDosage(drugName, dosage);
            if (exist) {
                return false;
            }
            Drug drug = new Drug(drugName, drugAmount, drugDescription, needPrescription, dosage, price);
            drugDao.add(drug);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Drug> findDrugByNeedPrescription(boolean value) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            List<Drug> drugList = drugDao.findByNeedPrescription(value);
            return drugList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean checkNeedPrescriptionByDrugNameAndDosage(String drugName, double dosage, boolean value) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            Optional<Boolean> needPrescription = drugDao.findNeedPrescriptionByDrugNameAndDosage(drugName, dosage);
            return needPrescription.orElse(false);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Integer> findDrugIdByDrugNameAndDosage(String drugName, double dosage) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            Optional<Integer> result = drugDao.findDrugIdByDrugNameAndDosage(drugName, dosage);
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Drug> findDrugByDrugNameAndDosage(String drugName, double dosage) throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            Optional<Drug> drugOptional = drugDao.findByNameAndDosage(drugName, dosage);
            return drugOptional;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public int findCurrentPaginationPage(String paginationPage, int currentPaginationPage) throws ServiceException {
        int result;
        if (paginationPage.equals(PAGINATION_PAGE_NEXT)) {
            if (currentPaginationPage + 1 <= countPaginationPageAmount()) {
                result = ++currentPaginationPage;
            } else {
                result = currentPaginationPage;
            }
        } else if (paginationPage.equals(PAGINATION_PAGE_PREVIOUS)) {
            if (currentPaginationPage > 1) {
                result = --currentPaginationPage;
            } else {
                result = currentPaginationPage;
            }
        } else {
            result = Integer.parseInt(paginationPage);
        }
        return result;
    }

    @Override
    public int countStartPaginationPage(int currentPaginationPage) {
        int possibleStartPaginationPage = currentPaginationPage - NUMBER_PAGINATION_LINKS / 2;
        int result = Math.max(possibleStartPaginationPage, 1);
        return result;
    }

    @Override
    public int countLastPaginationPage(int currentPaginationPage) throws ServiceException {
        int paginationPageAmount = countPaginationPageAmount();
        int possibleLastPaginationPage = currentPaginationPage + NUMBER_PAGINATION_LINKS / 2;
        int result = Math.min(possibleLastPaginationPage, paginationPageAmount);
        return result;
    }

    private int countPaginationPageAmount() throws ServiceException {
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            List<Drug> drugList = drugDao.findAll();
            int drugsNumber = drugList.size();
            int result = (int) Math.ceil((double) drugsNumber / DRUGS_NUMBER_PER_PAGE);
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }
}
