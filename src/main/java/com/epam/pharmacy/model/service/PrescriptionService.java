package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Prescription;

import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
    List<Prescription> findAllByCustomerIdWithDoctorNameAndDoctorSurnameAndDrugName(long customerId) throws ServiceException;

    void updateStatusById(Prescription.Status status, long prescriptionId) throws ServiceException;

    void addPrescriptionByDoctorIdAndCustomerIdAndDrugNameAndAmountAndStatus(long customerId, long doctorId, int drugId, int drugAmount, Prescription.Status status) throws ServiceException;

    List<Prescription> findAllByDoctorIdAndStatus(long doctorId, Prescription.Status status) throws ServiceException;

    Optional<Prescription> findPrescriptionByIdWithoutDoctor(long prescriptionId) throws ServiceException;

    void updateIssueDateAndEndDateAndStatusById(long prescriptionId, long issueDate, long endDate, Prescription.Status status) throws ServiceException;

    boolean checkPrescription(long customerId, String drugName) throws ServiceException;
}
