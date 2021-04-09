package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.PrescriptionDao;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.PrescriptionService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class-service for working with {@Prescription}.
 *
 * @author Yauheni Tsitou.
 * @see Prescription
 */
public class PrescriptionServiceImpl implements PrescriptionService {

    /**
     * Reference to an object of class {@code PrescriptionDao}.
     */
    private static final PrescriptionDao prescriptionDao = PrescriptionDao.getInstance();

    /**
     * Reference to an object of class {@code DrugDao}.
     */
    private static final DrugDao drugDao = DrugDao.getInstance();

    /**
     * Reference to an object of class {@code PrescriptionServiceImpl}.
     */
    private static final PrescriptionService instance = new PrescriptionServiceImpl();

    private PrescriptionServiceImpl() {
    }

    /**
     * Method that returns a reference to an object.
     *
     * @return Reference to an object of class {@code PrescriptionServiceImpl}.
     */
    public static PrescriptionService getInstance() {
        return instance;
    }

    @Override
    public List<Prescription> findAllByCustomerId(long customerId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            List<Prescription> prescriptionList = prescriptionDao.findAllByCustomerId(customerId);
            return prescriptionList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean updateStatusById(Prescription.Status status, long prescriptionId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(prescriptionDao);
        try {
            Optional<Prescription> prescriptionOptional = prescriptionDao.findById(prescriptionId);
            if (prescriptionOptional.isEmpty()) {
                return false;
            }
            Prescription prescription = prescriptionOptional.get();
            prescription.setStatus(status);
            prescriptionDao.update(prescription);
            transaction.commit();
            return true;
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }

    @Override
    public void add(long customerId, long doctorId, int drugId, int drugAmount, Prescription.Status status) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            User customer = new User(customerId);
            User doctor = new User(doctorId);
            Drug drug = new Drug(drugId);
            Date issueDate = new Date(0);
            Date endDate = new Date(0);
            Prescription prescription = new Prescription(customer, doctor, drug, drugAmount, issueDate, endDate, status);
            prescriptionDao.add(prescription);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Prescription> findAllByDoctorIdAndStatus(long doctorId, Prescription.Status status) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            int statusId = status.ordinal() + 1;
            List<Prescription> prescriptionList = prescriptionDao.findByDoctorIdAndStatusId(doctorId, statusId);
            return prescriptionList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Prescription> findPrescriptionById(long prescriptionId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            Optional<Prescription> prescription = prescriptionDao.findById(prescriptionId);
            return prescription;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean updateIssueDateAndEndDateAndStatusById(long prescriptionId, long issueDate, long endDate, Prescription.Status status) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            Optional<Prescription> prescriptionOptional = prescriptionDao.findById(prescriptionId);
            if (prescriptionOptional.isEmpty()) {
                return false;
            }
            Prescription prescription = prescriptionOptional.get();
            prescription.setIssueDate(new Date(issueDate));
            prescription.setEndDate(new Date(endDate));
            prescription.setStatus(status);
            prescriptionDao.update(prescription);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean checkPrescription(long customerId, String drugName, int dosage) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(drugDao, prescriptionDao);
        try {
            Optional<Drug> drugOptional = drugDao.findByNameAndDosage(drugName, dosage);
            if (drugOptional.isEmpty()) {
                return false;
            }
            Drug drug = drugOptional.get();
            boolean needPrescription = drug.isNeedPrescription();
            if (!needPrescription) {
                return true;
            }
            long drugId = drug.getId();
            boolean existPrescription = prescriptionDao.existPrescriptionByDrugId(drugId);
            transaction.commit();
            return existPrescription;
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }
}
