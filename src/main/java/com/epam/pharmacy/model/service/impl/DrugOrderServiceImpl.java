package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.*;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.DrugOrder;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugOrderService;
import com.epam.pharmacy.model.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Class-service for working with {@link DrugOrder}.
 *
 * @author Yauheni Tsitou.
 * @see DrugOrder
 */
public class DrugOrderServiceImpl implements DrugOrderService {

    /**
     * Reference to an object of class {@link DrugOrderServiceImpl}.
     */
    private static final DrugOrderService instance = new DrugOrderServiceImpl();

    /**
     * Reference to an object of class {@link UserServiceImpl}.
     */
    private static final UserService userService = UserServiceImpl.getInstance();

    private DrugOrderServiceImpl() {
    }

    /**
     * Method that returns a reference to an object
     *
     * @return Reference to an object of class {@code DrugOrderServiceImpl}.
     */
    public static DrugOrderService getInstance() {
        return instance;
    }

    @Override
    public boolean orderPayment(User customer, Drug drug, int drugAmount) throws ServiceException {
        UserDao userDao = new UserDao();
        DrugOrderDao drugOrderDao = new DrugOrderDao();
        DrugDao drugDao = new DrugDao();
        PrescriptionDao prescriptionDao = new PrescriptionDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(userDao, drugOrderDao, drugDao, prescriptionDao);
        try {
            BigDecimal drugPrice = drug.getPrice();
            BigDecimal orderPrice = drugPrice.multiply(BigDecimal.valueOf(drugAmount));
            int compareResult = customer.getAmount().compareTo(orderPrice);
            if (compareResult < 0) {
                return false;
            }
            boolean resultPrescriptionChange = true;
            if (drug.isNeedPrescription()) {
                resultPrescriptionChange = false;
                long customerId = customer.getId();
                long drugId = drug.getId();
                List<Prescription> prescriptionList = prescriptionDao.findByCustomerIdAndDrugId(customerId, drugId);
                int index = 0;
                int size = prescriptionList.size();
                while (index != size) {
                    Prescription prescription = prescriptionList.get(index);
                    int prescriptionAmount = prescription.getAmount();
                    if (prescriptionAmount >= drugAmount) {
                        prescriptionAmount -= drugAmount;
                        prescription.setAmount(prescriptionAmount);
                        prescriptionDao.update(prescription);
                        resultPrescriptionChange = true;
                        break;
                    }
                    index++;
                }
            }
            if (!resultPrescriptionChange) {
                return false;
            }
            BigDecimal oldCustomerAmount = customer.getAmount();
            BigDecimal newCustomerAmount = oldCustomerAmount.add(orderPrice.multiply(BigDecimal.valueOf(-1)));
            customer.setAmount(newCustomerAmount);
            userDao.update(customer);
            List<User> pharmacistList = userDao.findByRole(User.Role.PHARMACIST);
            User pharmacist = pharmacistList.get(0);
            BigDecimal oldPharmacistAmount = pharmacist.getAmount();
            BigDecimal newPharmacistAmount = oldPharmacistAmount.add(orderPrice);
            customer.setAmount(newPharmacistAmount);
            userDao.update(pharmacist);
            DrugOrder order = new DrugOrder(customer, drug, drugAmount, DrugOrder.Status.PROCESSING);
            drugOrderDao.add(order);
            int newDrugAmount = drug.getDrugAmount() - drugAmount;
            drug.setDrugAmount(newDrugAmount);
            drugDao.update(drug);
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
    public List<DrugOrder> findByCustomerId(long customerId) throws ServiceException {
        DrugOrderDao drugOrderDao = new DrugOrderDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugOrderDao);
        try {
            List<DrugOrder> drugOrderList = drugOrderDao.findByCustomerId(customerId);
            return drugOrderList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<DrugOrder> findByStatus(DrugOrder.Status status) throws ServiceException {
        DrugOrderDao drugOrderDao = new DrugOrderDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugOrderDao);
        try {
            List<DrugOrder> drugOrderList = drugOrderDao.findByStatus(status);
            return drugOrderList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public void updateStatusById(long drugOrderId, DrugOrder.Status status) throws ServiceException {
        DrugOrderDao drugOrderDao = new DrugOrderDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugOrderDao);
        try {
            int statusId = status.ordinal() + 1;
            drugOrderDao.updateStatusById(drugOrderId, statusId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean refuseDrugOrderService(long drugOrderId) throws ServiceException {
        UserDao userDao = new UserDao();
        DrugOrderDao drugOrderDao = new DrugOrderDao();
        DrugDao drugDao = new DrugDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(drugOrderDao, drugDao, userDao);
        try {
            int statusId = DrugOrder.Status.REJECTED.ordinal() + 1;
            drugOrderDao.updateStatusById(drugOrderId, statusId);
            Optional<DrugOrder> drugOrderOptional = drugOrderDao.findById(drugOrderId);
            if (drugOrderOptional.isEmpty()) {
                return false;
            }
            DrugOrder drugOrder = drugOrderOptional.get();
            Drug drug = drugOrder.getDrug();
            BigDecimal drugPrice = drug.getPrice();
            int orderDrugsNumber = drugOrder.getDrugsNumber();
            int oldDrugAmount = drug.getDrugAmount();
            int newAmount = oldDrugAmount + orderDrugsNumber;
            drug.setDrugAmount(newAmount);
            drugDao.update(drug);
            User customer = drugOrder.getCustomer();
            BigDecimal orderPrice = drugPrice.multiply(BigDecimal.valueOf(orderDrugsNumber));
            BigDecimal oldCustomerAmount = customer.getAmount();
            BigDecimal newCustomerAmount = oldCustomerAmount.add(orderPrice);
            customer.setAmount(newCustomerAmount);
            userDao.update(customer);
            User pharmacist = userDao.findByRole(User.Role.PHARMACIST).get(0);
            BigDecimal oldPharmacistAmount = customer.getAmount();
            BigDecimal newPharmacistAmount = oldPharmacistAmount.add(orderPrice.multiply(BigDecimal.valueOf(-1)));
            customer.setAmount(newPharmacistAmount);
            userDao.update(pharmacist);
            transaction.commit();
            return true;
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.endTransaction();
        }
    }
}
