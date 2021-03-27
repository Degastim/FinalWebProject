package com.epam.Pharmacy.model.service;

import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
    List<Prescription> findAllByCustomerIdWithDoctorNameSurnameAndDrugNameWithoutPrescriptionId(long customerId) throws ServiceException;

    void updateStatusById(Prescription.Status status, long prescriptionId) throws ServiceException;

    void createPrescriptionByDoctorIdAndCustomerIdAndDrugNameAndAmountAndStatus(long customerId, long doctorId, int drugId, int drugAmount, Prescription.Status status) throws ServiceException;

    List<Prescription> findAllByDoctorIdAndStatus(long doctorId, Prescription.Status status) throws ServiceException;

    Optional<Prescription> findPrescriptionByIdWithoutDoctor(long prescriptionId) throws ServiceException;

    void updateIssueDateAndEndDateAndStatusById(long prescriptionId, long issueDate, long endDate, Prescription.Status status) throws ServiceException;

    boolean checkPrescription(long customerId, String drugName) throws ServiceException;
}
