package com.epam.Pharmacy.model.service;

import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean add(User user, String password) throws ServiceException;

    Optional<User> findByEmailPassword(String email, String password) throws ServiceException;

    boolean updateByPassword(long id, String newPassword, String oldPassword) throws ServiceException;

    List<User> findByRole(User.UserRole role) throws ServiceException;

    boolean checkRegistrationForm(String name, String surname, String password, String email) throws ServiceException;
}
