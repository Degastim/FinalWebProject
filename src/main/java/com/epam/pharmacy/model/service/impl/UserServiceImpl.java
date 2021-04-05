package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.EntityTransaction;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.model.validator.UserValidator;
import com.epam.pharmacy.util.Encrypter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final UserService instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    private static final UserDao userDao = UserDao.getInstance();

    @Override
    public boolean add(User user, String password) throws ServiceException {
        boolean result = false;
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        if (UserValidator.isNameValid(name) && UserValidator.isSurnameValid(surname) && UserValidator.isPasswordValid(password) && UserValidator.isEmailValid(email)) {
            try {
                userDao.add(user);
                result = true;
            } catch (DaoException e) {
                transaction.rollback();
                throw new ServiceException(e);
            } finally {
                transaction.end();
            }
        }
        return result;
    }

    @Override
    public Optional<User> findByEmailPassword(String email, String password) throws ServiceException {
        Optional<User> result;
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        try {
            result = userDao.findByEmailPassword(email, Encrypter.encrypt(password));
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public boolean updateByPassword(long userId, String newPassword, String oldPassword) throws ServiceException {
        boolean result = false;
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        try {
            boolean passwordComparisonResult = userDao.existByPasswordAndUserId(userId, Encrypter.encrypt(oldPassword));
            if (passwordComparisonResult) {
                if (UserValidator.isPasswordValid(newPassword)) {
                    userDao.updateUserByPassword(userId, Encrypter.encrypt(newPassword));
                    result = true;
                }
            }
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public List<User> findByRole(User.Role role) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        try {
            List<User> userList = userDao.findByRole(role);
            return userList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public boolean checkRegistrationForm(String name, String surname, String password, String email) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        try {
            return UserValidator.isNameValid(name) && UserValidator.isSurnameValid(surname) && UserValidator.isPasswordValid(password) && UserValidator.isEmailValid(email) && !userDao.existByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public void updateByAmount(User user, BigDecimal amount) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        try {
            BigDecimal oldAmount = user.getAmount();
            BigDecimal newAmount = oldAmount.add(amount);
            user.setAmount(newAmount);
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @Override
    public Optional<User> findById(long userId) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        try {
            Optional<User> user = userDao.findById(userId);
            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }
}