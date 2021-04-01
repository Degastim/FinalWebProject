package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Drug;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DrugDao {
    Optional<Drug> findByIdWithImages(int id) throws DaoException;

    int showDrugsNumber() throws DaoException;

    List<Drug> findAllDrugs() throws DaoException;

    void updateDrug(int drugId, String drugName, int drugAmount, String drugDescription, boolean needPrescription, BigDecimal price, int dosage) throws DaoException;

    void deleteById(int drugId) throws DaoException;

    void add(String drugName, int drugAmount, String drugDescription, boolean needPrescription, int dosage, BigDecimal price) throws DaoException;

    List<Drug> findDrugNameAndIdWithNeedPrescription(boolean value) throws DaoException;

    Optional<Boolean> findNeedPrescriptionByDrugName(String drugName) throws DaoException;

    int findDrugByDrugName(String drugName) throws DaoException;

    Optional<Drug> findById(int drugId) throws DaoException;

    boolean existDrugByNameAndDosage(String drugName, int dosage) throws DaoException;

    Optional<Integer> findDrugIdByDrugName(String drugName) throws DaoException;

    Optional<Integer> findAmountById(int drugId) throws DaoException;

    void updateAmountById(int drugId,int drugAmount) throws DaoException;

    Optional<Drug> findByNameAndDosage(String drugName, int dosage) throws DaoException;
}
