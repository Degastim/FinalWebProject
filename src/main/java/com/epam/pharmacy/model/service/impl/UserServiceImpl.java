package com.epam.pharmacy.model.service.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.dao.impl.UserDaoImpl;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.model.validator.UserValidator;
import com.epam.pharmacy.util.Encrypter;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final UserService instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        return instance;
    }

    private static final UserDao dao = UserDaoImpl.getInstance();

    @Override
    public boolean add(User user, String password) throws ServiceException {
        boolean result = false;
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        if (UserValidator.isNameValid(name) && UserValidator.isSurnameValid(surname) && UserValidator.isPasswordValid(password) && UserValidator.isEmailValid(email)) {
            try {
                dao.add(user, Encrypter.encrypt(password));
                result = true;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public Optional<User> findByEmailPassword(String email, String password) throws ServiceException {
        Optional<User> result;
        try {
            result = dao.findByEmailPassword(email, Encrypter.encrypt(password));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateByPassword(long userId, String newPassword, String oldPassword) throws ServiceException {
        boolean result = false;
        try {
            boolean passwordComparisonResult=dao.existByPasswordAndUserId(userId,Encrypter.encrypt(oldPassword));
            if(passwordComparisonResult) {
                if (UserValidator.isPasswordValid(newPassword)) {
                    dao.updateUserByPassword(userId, Encrypter.encrypt(newPassword));
                    result = true;
                }
            }
            return result;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findByRole(User.UserRole role) throws ServiceException {
        try {
            List<User> userList = dao.findByRole(role);
            return userList;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkRegistrationForm(String name, String surname, String password, String email) throws ServiceException {
        try {
            return UserValidator.isNameValid(name) && UserValidator.isSurnameValid(surname) && UserValidator.isPasswordValid(password) && UserValidator.isEmailValid(email) && dao.existByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}