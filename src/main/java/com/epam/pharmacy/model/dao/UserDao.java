package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    void add(String name, String surname, String email, String encryptPassword, int roleId) throws DaoException;

    void updateUserByPassword(long id, String newPassword) throws DaoException;

    Optional<User> findByEmailPassword(String email, String encryptPassword) throws DaoException;

    List<User> findByRole(User.UserRole role) throws DaoException;

    boolean existByEmail(String email) throws DaoException;

    boolean existByPasswordAndUserId(long userId, String password) throws DaoException;

    void orderDrugByCustomerIdAndPharmacistId(long customerId, long pharmacistId, BigDecimal value) throws DaoException;
}
