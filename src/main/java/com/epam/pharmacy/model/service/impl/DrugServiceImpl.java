package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugDao;
import com.epam.pharmacy.model.dao.impl.DrugDaoImpl;
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

    private static final DrugDao drugDao = DrugDaoImpl.getInstance();
    private static final int DRUGS_NUMBER_PER_PAGE = 4;
    private static final String PAGINATION_PAGE_NEXT = "next";
    private static final String Pagination_PAGE_PREVIOUS = "previous";
    private static final int NUMBER_PAGINATION_LINKS = 3;

    @Override
    public List<Drug> findAllDrugs() throws ServiceException {
        try {
            List<Drug> result = drugDao.findAllDrugs();
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countPaginationPageAmount() throws ServiceException {
        try {
            int result = (int) Math.ceil((double) drugDao.showDrugsNumber() / DRUGS_NUMBER_PER_PAGE);
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Drug> findByIdWithImages(int drugId) throws ServiceException {
        try {
            Optional<Drug> drug = drugDao.findByIdWithImages(drugId);
            return drug;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateDrug(int drugId, String drugName, int drugAmount, String drugDescription, boolean needPrescription, BigDecimal price, int dosage) throws ServiceException {
        try {
            Optional<Drug> drug = drugDao.findByNameAndDosage(drugName, dosage);
            if (drug.isEmpty()) {
                drugDao.updateDrug(drugId, drugName, drugAmount, drugDescription, needPrescription, price, dosage);
                return true;
            } else {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public List<Drug> findPaginationDrugs(int currentPaginationPage) throws ServiceException {
        List<Drug> drugList = new ArrayList<>();
        int lastDrugNumberPerPage = DRUGS_NUMBER_PER_PAGE * currentPaginationPage;
        try {
            Optional<Drug> drugOptional = drugDao.findByIdWithImages(lastDrugNumberPerPage - 3);
            if (drugOptional.isPresent()) {
                Drug drug = drugOptional.get();
                drugList.add(drug);
                drugOptional = drugDao.findByIdWithImages(lastDrugNumberPerPage - 2);
                if (drugOptional.isPresent()) {
                    drug = drugOptional.get();
                    drugList.add(drug);
                    drugOptional = drugDao.findByIdWithImages(lastDrugNumberPerPage - 1);
                    if (drugOptional.isPresent()) {
                        drug = drugOptional.get();
                        drugList.add(drug);
                        drugOptional = drugDao.findByIdWithImages(lastDrugNumberPerPage);
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
        }

    }

    @Override
    public void deleteById(int drugId) throws ServiceException {
        try {
            drugDao.deleteById(drugId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean add(String drugName, int drugAmount, String drugDescription, boolean needPrescription, int dosage, BigDecimal price) throws ServiceException {
        try {
            Optional<Drug> drug = drugDao.findByNameAndDosage(drugName, dosage);
            if (drug.isEmpty()) {
                drugDao.add(drugName, drugAmount, drugDescription, needPrescription, dosage, price);
                return true;
            } else {
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Drug> findDrugNameAndIdWithNeedPrescription(boolean value) throws ServiceException {
        try {
            List<Drug> drugList = drugDao.findDrugNameAndIdWithNeedPrescription(value);
            return drugList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkNeedPrescriptionByDrugName(String drugName, boolean value) throws ServiceException {
        try {
            Optional<Boolean> needPrescription = drugDao.findNeedPrescriptionByDrugName(drugName);
            return needPrescription.orElse(false);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public int findDrugIdByDrugName(String drugName) throws ServiceException {
        try {
            int result = drugDao.findDrugByDrugName(drugName);
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Drug> findDrugByDrugNameAndDosage(String drugName, int dosage) throws ServiceException {
        try {
            Optional<Drug> drugOptional = drugDao.findByNameAndDosage(drugName, dosage);
            return drugOptional;
        } catch (DaoException e) {
            throw new ServiceException(e);
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
