package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.DrugDao;
import com.epam.pharmacy.model.dao.DrugOrderDao;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugOrderService;
import com.epam.pharmacy.model.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DrugOrderServiceImpl implements DrugOrderService {
    private static final DrugOrderService instance = new DrugOrderServiceImpl();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final UserDao userDao = UserDao.getInstance();
    private static final DrugOrderDao drugOrderDao = DrugOrderDao.getInstance();
    private static final DrugDao drugDao = DrugDao.getInstance();

    private DrugOrderServiceImpl() {
    }

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
            Order order = new Order(customer, drug, drugAmount, Order.Status.PROCESSING);
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
    public List<Order> findByCustomerId(long customerId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugOrderDao);
        try {
            List<Order> drugOrderList = drugOrderDao.findByCustomerId(customerId);
            return drugOrderList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<Order> findByStatus(Order.Status status) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(drugOrderDao);
        try {
            List<Order> drugOrderList = drugOrderDao.findByStatus(status);
            return drugOrderList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public void updateStatusById(long drugOrderId, Order.Status status) throws ServiceException {
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
            long statusId = Order.Status.REJECTED.ordinal() + 1;
            drugOrderDao.updateStatusById(drugOrderId, statusId);
            Optional<Order> drugOrderOptional = drugOrderDao.findById(drugOrderId);
            if (drugOrderOptional.isEmpty()) {
                return false;
            }
            Order drugOrder = drugOrderOptional.get();
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
