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

import java.util.List;
import java.util.Optional;

public class PrescriptionServiceImpl implements PrescriptionService {
    private static final PrescriptionDao prescriptionDao = PrescriptionDao.getInstance();
    private static final DrugDao drugDao = DrugDao.getInstance();
    private static final PrescriptionService instance = new PrescriptionServiceImpl();

    private PrescriptionServiceImpl() {
    }

    public static PrescriptionService getInstance() {
        return instance;
    }

    @Override
    public List<Prescription> findAllByCustomerIdWithDoctorNameAndDoctorSurnameAndDrugName(long customerId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            List<Prescription> prescriptionList = prescriptionDao.findAllByCustomerIdWithDoctorNameAndDoctorSurnameAndDrugName(customerId);
            return prescriptionList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public void updateStatusById(Prescription.Status status, long prescriptionId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            int statusId = status.ordinal() + 1;
            prescriptionDao.updateStatusById(statusId, prescriptionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
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
            Prescription prescription = new Prescription(customer, doctor, drug, drugAmount, status);
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
            List<Prescription> prescriptionList = prescriptionDao.findPrescriptionIdAndCustomerNameAndCustomerSurNameAndDrugNameAndAmountByDoctorIdAndStatusId(doctorId, statusId);
            return prescriptionList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<Prescription> findPrescriptionByIdWithoutDoctor(long prescriptionId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            Optional<Prescription> prescription = prescriptionDao.findPrescriptionByIdWithoutDoctor(prescriptionId);
            return prescription;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public void updateIssueDateAndEndDateAndStatusById(long prescriptionId, long issueDate, long endDate, Prescription.Status status) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(prescriptionDao);
        try {
            int statusId = status.ordinal() + 1;
            prescriptionDao.updateIssueDateAndEndDateAndStatusIdById(prescriptionId, issueDate, endDate, statusId);
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
