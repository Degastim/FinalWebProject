package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugDao;
import com.epam.pharmacy.model.dao.DrugOrderDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.DrugOrder;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugOrderService;
import com.epam.pharmacy.model.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Class-service for working with {@code Order}.
 * @see DrugOrder
 * @author Yauheni Tsitou.
 */
public class DrugOrderServiceImpl implements DrugOrderService {

    /**
     * Reference to an object of class {@code DrugOrderServiceImpl}.
     */
    private static final DrugOrderService instance = new DrugOrderServiceImpl();

    /**
     * Reference to an object of class {@code UserServiceImpl}.
     */
    private static final UserService userService = UserServiceImpl.getInstance();

    /**
     * Reference to an object of class {@code UserDao}.
     */
    private static final UserDao userDao = UserDao.getInstance();
    /**
     * Reference to an object of class {@code DrugOrderDao}.
     */
    private static final DrugOrderDao drugOrderDao = DrugOrderDao.getInstance();

    /**
     * Reference to an object of class {@code DrugDao}.
     */
    private static final DrugDao drugDao = DrugDao.getInstance();

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
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(userDao, drugOrderDao, drugDao);
        try {
            BigDecimal drugPrice = drug.getPrice();
            BigDecimal orderPrice = drugPrice.multiply(BigDecimal.valueOf(drugAmount));
            int compareResult = customer.getAmount().compareTo(orderPrice);
            if (compareResult < 0) {
                return false;
            }
            userService.updateByAmount(customer, orderPrice.multiply(BigDecimal.valueOf(-1)));
            List<User> pharmacistList = userDao.findByRole(User.Role.PHARMACIST);
            User pharmacist = pharmacistList.get(0);
            userService.updateByAmount(pharmacist, orderPrice);
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
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugOrderDao);
        try {
            long statusId = status.ordinal() + 1;
            drugOrderDao.updateStatusById(drugOrderId, statusId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean refuseDrugOrderService(long drugOrderId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(drugOrderDao, drugDao, userDao);
        try {
            long statusId = DrugOrder.Status.REJECTED.ordinal() + 1;
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
            userService.updateByAmount(customer, orderPrice);
            User pharmacist = userDao.findByRole(User.Role.PHARMACIST).get(0);
            userService.updateByAmount(pharmacist, orderPrice.multiply(BigDecimal.valueOf(-1)));
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
