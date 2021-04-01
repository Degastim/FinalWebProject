package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugDao;
import com.epam.pharmacy.model.dao.PrescriptionDao;
import com.epam.pharmacy.model.dao.impl.DrugDaoImpl;
import com.epam.pharmacy.model.dao.impl.PrescriptionDaoImpl;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.service.PrescriptionService;

import java.util.List;
import java.util.Optional;

public class PrescriptionServiceImpl implements PrescriptionService {
    private static final PrescriptionDao prescriptionDao = PrescriptionDaoImpl.getInstance();
    private static final DrugDao drugDao = DrugDaoImpl.getInstance();
    private static final PrescriptionService instance = new PrescriptionServiceImpl();

    private PrescriptionServiceImpl() {
    }

    public static PrescriptionService getInstance() {
        return instance;
    }

    @Override
    public List<Prescription> findAllByCustomerIdWithDoctorNameAndDoctorSurnameAndDrugName(long customerId) throws ServiceException {
        try {
            List<Prescription> prescriptionList = prescriptionDao.findAllByCustomerIdWithDoctorNameAndDoctorSurnameAndDrugName(customerId);
            return prescriptionList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateStatusById(Prescription.Status status, long prescriptionId) throws ServiceException {
        try {
            int statusId = status.ordinal() + 1;
            prescriptionDao.updateStatusById(statusId, prescriptionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addPrescriptionByDoctorIdAndCustomerIdAndDrugNameAndAmountAndStatus(long customerId, long doctorId, int drugId, int drugAmount, Prescription.Status status) throws ServiceException {
        try {
            int statusId = status.ordinal() + 1;
            prescriptionDao.addPrescriptionByDoctorIdAndCustomerIdAndDrugNameAndAmountAndStatusId(customerId, doctorId, drugId, drugAmount, statusId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Prescription> findAllByDoctorIdAndStatus(long doctorId, Prescription.Status status) throws ServiceException {
        try {
            int statusId = status.ordinal() + 1;
            List<Prescription> prescriptionList = prescriptionDao.findPrescriptionIdAndCustomerNameAndCustomerSurNameAndDrugNameAndAmountByDoctorIdAndStatusId(doctorId, statusId);
            return prescriptionList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Prescription> findPrescriptionByIdWithoutDoctor(long prescriptionId) throws ServiceException {
        try {
            Optional<Prescription> prescription = prescriptionDao.findPrescriptionByIdWithoutDoctor(prescriptionId);
            return prescription;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateIssueDateAndEndDateAndStatusById(long prescriptionId, long issueDate, long endDate, Prescription.Status status) throws ServiceException {
        try {
            int statusId = status.ordinal() + 1;
            prescriptionDao.updateIssueDateAndEndDateAndStatusIdById(prescriptionId, issueDate, endDate, statusId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkPrescription(long customerId, String drugName) throws ServiceException {
        try {
            Optional<Boolean> needPrescriptionOptional = drugDao.findNeedPrescriptionByDrugName(drugName);
            if (needPrescriptionOptional.isEmpty()) {
                return false;
            }
            boolean needPrescription = needPrescriptionOptional.get();
            if (!needPrescription) {
                return true;
            }
            Optional<Integer> drugIdOptional = drugDao.findDrugIdByDrugName(drugName);
            if (drugIdOptional.isEmpty()) {
                return false;
            }
            int drugId = drugIdOptional.get();
            boolean existPrescription = prescriptionDao.existPrescriptionByDrugId(drugId);
            return existPrescription;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
