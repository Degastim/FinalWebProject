package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DrugService {

    List<Drug> findAllDrugs() throws ServiceException;

    int countPaginationPageAmount() throws ServiceException;

    Optional<Drug> findByIdWithImages(int drugId) throws ServiceException;

    boolean updateDrug(int drugId, String drugName, int drugAmount, String drugDescription, boolean needPrescription, BigDecimal price, int dosage) throws ServiceException;

    List<Drug> findPaginationDrugs(int startPaginationPage) throws ServiceException;

    void deleteById(int drugId) throws ServiceException;

    boolean add(String drugName, int drugAmount, String drugDescription, boolean needPrescription, int dosage, BigDecimal price) throws ServiceException;

    List<Drug> findDrugNameAndIdWithNeedPrescription(boolean value) throws ServiceException;

    boolean checkNeedPrescriptionByDrugName(String drugName, boolean value) throws ServiceException;

    int findDrugIdByDrugName(String drugName) throws ServiceException;

    Optional<Drug> findDrugByDrugNameAndDosage(String drugName, int dosage) throws ServiceException;

    int findCurrentPaginationPage(String paginationPage, int currentPaginationPage) throws ServiceException;

    int countStartPaginationPage(int currentPaginationPage);

    int countLastPaginationPage(int currentPaginationPage, int paginationPageAmount);
}
