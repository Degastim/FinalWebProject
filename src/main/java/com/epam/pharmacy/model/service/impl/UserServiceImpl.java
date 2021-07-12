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

/**
 * Class-service for working with {@link User}.
 *
 * @author Yauheni Tsitou.
 * @see User
 */
public class UserServiceImpl implements UserService {

    /**
     * Reference to an object of class {@link UserServiceImpl}.
     */
    private static final UserService instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    /**
     * Method that returns a reference to an object.
     *
     * @return Reference to an object of class {@link UserServiceImpl}.
     */
    public static UserService getInstance() {
        return instance;
    }

    @Override
    public boolean add(User user, String password) throws ServiceException {
        UserDao userDao = new UserDao();
        boolean result = false;
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        EntityTransaction transaction = new EntityTransaction();
        transaction.initTransaction(userDao);
        if (UserValidator.isNameValid(name) && UserValidator.isSurnameValid(surname) && UserValidator.isEmailValid(email)) {
            try {
                userDao.add(user);
                long userId=user.getId();
                userDao.updatePasswordByUserId(userId, password);
                result = true;
                transaction.commit();
            } catch (DaoException e) {
                transaction.rollback();
                throw new ServiceException(e);
            } finally {
                transaction.endTransaction();
            }
        }
        return result;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException {
        UserDao userDao = new UserDao();
        Optional<User> userOptional;
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        try {
            userOptional = userDao.findByEmail(email);
            Optional<String> encryptPasswordOptional = userDao.findPasswordByEmail(email);
            if (encryptPasswordOptional.isEmpty()) {
                return userOptional;
            }
            String encryptPassword = encryptPasswordOptional.get();
            boolean check = Encrypter.check(password, encryptPassword);
            if (check) {
                return userOptional;
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }

    }

    @Override
    public boolean updateByPassword(User user, String newPassword, String oldPassword) throws ServiceException {
        UserDao userDao = new UserDao();
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        boolean result = false;
        try {
            String email = user.getEmail();
            Optional<String> passwordDaoOptional = userDao.findPasswordByEmail(email);
            if (passwordDaoOptional.isEmpty()) {
                return false;
            }
            String passwordDao = passwordDaoOptional.get();
            if (!passwordDao.equals(oldPassword)) {
                return false;
            }
            if (UserValidator.isPasswordValid(newPassword)) {
                long userId = user.getId();
                userDao.updatePasswordByUserId(userId, Encrypter.encrypt(newPassword));
                result = true;
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
        UserDao userDao = new UserDao();
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
        UserDao userDao = new UserDao();
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
    public void updateByAddAmount(User user, BigDecimal amount) throws ServiceException {
        UserDao userDao = new UserDao();
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
        UserDao userDao = new UserDao();
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