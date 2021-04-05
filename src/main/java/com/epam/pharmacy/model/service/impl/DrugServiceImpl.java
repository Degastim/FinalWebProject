package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.service.DrugService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrugServiceImpl implements DrugService {
    private static final DrugService instance = new DrugServiceImpl();

    private DrugServiceImpl() {
    }

    public static DrugService getInstance() {
        return instance;
    }

    private static final DrugDao drugDao = DrugDao.getInstance();
    private static final int DRUGS_NUMBER_PER_PAGE = 4;
    private static final String PAGINATION_PAGE_NEXT = "next";
    private static final String Pagination_PAGE_PREVIOUS = "previous";
    private static final int NUMBER_PAGINATION_LINKS = 3;

    @Override
    public List<Drug> findAllDrugs() throws ServiceException {
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
    public int countPaginationPageAmount() throws ServiceException {
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

    @Override
    public Optional<Drug> findByIdWithImages(long drugId) throws ServiceException {
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
    public boolean updateDrug(int drugId, String drugName, int drugAmount, String description, boolean needPrescription, BigDecimal price, int dosage) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            Optional<Drug> drugDaoOptional = drugDao.findByNameAndDosage(drugName, dosage);
            if (drugDaoOptional.isPresent()) {
                Drug drug = drugDaoOptional.get();
                if (drug.getId() == drugId) {
                    drug = new Drug(drugId, drugName, drugAmount, description, needPrescription, dosage, price);
                    drugDao.update(drug);
                    return true;
                } else {
                    return false;
                }
            } else {
                Drug drug = new Drug(drugId, drugName, drugAmount, description, needPrescription, dosage, price);
                drugDao.update(drug);
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }


    public List<Drug> findPaginationDrugs(int currentPaginationPage) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        List<Drug> drugList = new ArrayList<>();
        int lastDrugNumberPerPage = DRUGS_NUMBER_PER_PAGE * currentPaginationPage;
        try {
            Optional<Drug> drugOptional = drugDao.findById(lastDrugNumberPerPage - 3);
            if (drugOptional.isPresent()) {
                Drug drug = drugOptional.get();
                drugList.add(drug);
                drugOptional = drugDao.findById(lastDrugNumberPerPage - 2);
                if (drugOptional.isPresent()) {
                    drug = drugOptional.get();
                    drugList.add(drug);
                    drugOptional = drugDao.findById(lastDrugNumberPerPage - 1);
                    if (drugOptional.isPresent()) {
                        drug = drugOptional.get();
                        drugList.add(drug);
                        drugOptional = drugDao.findById(lastDrugNumberPerPage);
                        if (drugOptional.isPresent()) {
                            drug = drugOptional.get();
                            drugList.add(drug);
                        }
                    }
                }
            }
            return drugList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public void deleteById(int drugId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(drugDao);
        try {
            drugDao.delete(drugId);
            drugDao.changeAutoincrement(drugId);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }

    @Override
    public boolean add(String drugName, int drugAmount, String drugDescription, boolean needPrescription, int dosage, BigDecimal price) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
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
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            List<Drug> drugList = drugDao.findDrugByNeedPrescription(value);
            return drugList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean checkNeedPrescriptionByDrugNameAndDosage(String drugName, int dosage, boolean value) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugDao);
        try {
            Optional<Boolean> needPrescription = drugDao.checkNeedPrescriptionByDrugNameAndDosage(drugName, dosage);
            return needPrescription.orElse(false);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Integer> findDrugIdByDrugNameAndDosage(String drugName, int dosage) throws ServiceException {
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
    public Optional<Drug> findDrugByDrugNameAndDosage(String drugName, int dosage) throws ServiceException {
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
        } else if (paginationPage.equals(Pagination_PAGE_PREVIOUS)) {
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
    public int countLastPaginationPage(int currentPaginationPage, int paginationPageAmount) {
        int possibleLastPaginationPage = currentPaginationPage + NUMBER_PAGINATION_LINKS / 2;
        int result = Math.min(possibleLastPaginationPage, paginationPageAmount);
        return result;
    }
}
