package com.epam.Pharmacy.model.dao;

import com.epam.Pharmacy.exception.DaoException;
import com.epam.Pharmacy.model.entity.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao {
    List<Prescription> findAllByCustomerIdWithDoctorNameSurnameAndDrugNameWithoutPrescriptionId(long customerId) throws DaoException;

    void updateStatusById(int statusId, long prescriptionId) throws DaoException;

    void createPrescriptionByDoctorIdAndCustomerIdAndDrugNameAndAmountAndStatusId(long customerId, long doctorId, int drugId, int drugAmount, int statusId) throws DaoException;

    List<Prescription> findPrescriptionIdAndCustomerNameAndCustomerSurNameAndDrugNameAndAmountByDoctorIdAndStatusId(long doctorId, int statusId) throws DaoException;

    Optional<Prescription> findPrescriptionByIdWithoutDoctor(long prescriptionId) throws DaoException;

    void updateIssueDateAndEndDateAndStatusIdById(long prescriptionId, long issueDate, long endDate, int statusId) throws DaoException;

    boolean existPrescriptionByDrugId(int drugId) throws DaoException;
}
