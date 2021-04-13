package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.Prescription;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides actions on {@link Prescription}.
 *
 * @author Yauheni Tsitou.
 */
public interface PrescriptionService {
    /**
     * Searches all recipes for a given customer
     *
     * @param customerId long value presription customer ID.
     * @return List of recipes for this customer.
     * @throws ServiceException if an error occurs while processing.
     */
    List<Prescription> findAllByCustomerId(long customerId) throws ServiceException;

    /**
     * Updates the status of a prescription by its id
     *
     * @param status         {@link Prescription.Status} object new status for prescription.
     * @param prescriptionId long value prescription ID to be updated
     * @return true if the drug was updated.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean updateStatusById(Prescription.Status status, long prescriptionId) throws ServiceException;

    /**
     * Adds a new prescription to the database.
     *
     * @param customerId long value prescription customer ID.
     * @param doctorId   long value prescription doctor ID.
     * @param drugId     long value prescription drug ID.
     * @param drugAmount int value prescription drug amount.
     * @param status     {@link Prescription.Status} object new status for prescription.
     * @throws ServiceException if an error occurs while processing.
     */
    void add(long customerId, long doctorId, int drugId, int drugAmount, Prescription.Status status) throws ServiceException;

    /**
     * Searches for all prescriptions issued by the given doctor and the required status.
     *
     * @param doctorId long value prescription doctor ID.
     * @param status   {@link Prescription.Status} object new status for prescription.
     * @return {@link List} of prescriptions issued by the given doctor and the required status
     * @throws ServiceException if an error occurs while processing.
     */
    List<Prescription> findAllByDoctorIdAndStatus(long doctorId, Prescription.Status status) throws ServiceException;

    /**
     * Looking for a recipe by his id
     *
     * @param prescriptionId long value prescription ID.
     * @return {@link Optional<Prescription>} object search results.
     * @throws ServiceException if an error occurs while processing.
     */
    Optional<Prescription> findPrescriptionById(long prescriptionId) throws ServiceException;

    /**
     * Updates the status,issueDate,endDate of a prescription by its id
     *
     * @param prescriptionId long value prescription ID.
     * @param issueDate      long value the number of milliseconds since January 1, 1970, 00:00:00 GMT to the date of issue of the prescription
     * @param endDate        long value the number of milliseconds since January 1, 1970, 00:00:00 GMT to the expiry date of the recipe
     * @param status         {@link Prescription.Status} object new status for prescription.
     * @return true if the drug was updated.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean updateIssueDateAndEndDateAndStatusById(long prescriptionId, long issueDate, long endDate, Prescription.Status status) throws ServiceException;

    /**
     * Checks whether a prescription is needed and whether the customer has one for the given drug
     *
     * @param customerId long value prescription customer ID.
     * @param drug {@link Drug} object to search for a prescription.
     * @return boolean value Result of checking.
     * @throws ServiceException if an error occurs while processing.
     */
    boolean checkPrescription(long customerId, Drug drug) throws ServiceException;
}
