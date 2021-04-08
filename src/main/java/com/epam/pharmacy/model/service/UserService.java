package com.epam.pharmacy.model.service;

import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean add(User user, String password) throws ServiceException;

    Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException;

    boolean updateByPassword(long id, String newPassword, String oldPassword) throws ServiceException;

    List<User> findByRole(User.Role role) throws ServiceException;

    boolean checkRegistrationForm(String name, String surname, String password, String email) throws ServiceException;

    void updateByAmount(User user, BigDecimal amount) throws ServiceException;

    Optional<User> findById(long userId) throws ServiceException;
}
