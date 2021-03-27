package com.epam.Pharmacy.model.service.impl;

import com.epam.Pharmacy.exception.DaoException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.dao.DrugDao;
import com.epam.Pharmacy.model.dao.DrugOrderDao;
import com.epam.Pharmacy.model.dao.UserDao;
import com.epam.Pharmacy.model.dao.impl.DrugDaoImpl;
import com.epam.Pharmacy.model.dao.impl.DrugOrderDaoImpl;
import com.epam.Pharmacy.model.dao.impl.UserDaoImpl;
import com.epam.Pharmacy.model.entity.Drug;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.DrugOrderService;

import java.math.BigDecimal;
import java.util.List;

public class DrugOrderServiceImpl implements DrugOrderService {
    private static final DrugOrderService instance = new DrugOrderServiceImpl();

    private static final UserDao userDao = UserDaoImpl.getInstance();
    private static final DrugOrderDao drugOrderDao= DrugOrderDaoImpl.getInstance();

    private DrugOrderServiceImpl() {
    }

    public static DrugOrderService getInstance() {
        return instance;
    }

    @Override
    public boolean orderPayment(User customer, Drug drug, int drugAmount) throws ServiceException {
        BigDecimal drugPrice = drug.getPrice();
        BigDecimal orderPrice = drugPrice.multiply(BigDecimal.valueOf(drugAmount));
        try {
            int compareResult = customer.getAmount().compareTo(orderPrice);
            if (compareResult < 0) {
                return false;
            }
            List<User> pharmacistList = userDao.findByRole(User.UserRole.PHARMACIST);
            User pharmacist = pharmacistList.get(0);
            long pharmacistId = pharmacist.getUserId();
            long customerId = customer.getUserId();
            int drugId=drug.getDrugId();
            userDao.orderDrugByCustomerIdAndPharmacistId(customerId, pharmacistId, orderPrice);
            drugOrderDao.add(customerId,drugId,drugAmount);
            return true;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}