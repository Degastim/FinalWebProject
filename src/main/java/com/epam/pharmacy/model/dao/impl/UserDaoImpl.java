package com.epam.pharmacy.model.dao.impl;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.dao.UserDao;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_ADD_USERS = "INSERT INTO webdb.users(name,surname, email,password,role_id) VALUES (?,?,?,?,?)";
    private static final String SQL_SELECT_USER_BY_EMAIL_PASSWORD = "SELECT user_id, users.name, users.surname,roles.role, users.amount FROM webdb.users JOIN webdb.roles ON users.role_id=roles.role_id  where email=? and password=?";
    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE webdb.users SET password=? WHERE user_id=?";
    private static final String SQL_SELECT_PASSWORD_BY_ID = "SELECT password FROM webdb.users WHERE user_id=?";
    private static final String SQL_SELECT_USERS_BY_ROLE = "SELECT user_id, name, surname FROM webdb.users LEFT JOIN webdb.roles ON users.role_id=roles.role_id where role=?";
    private static final String SQL_EXIST_BY_EMAIL = "SELECT EXISTS (SELECT * FROM webdb.users WHERE email = ?) AS exist";
    private static final String SQL_EXIST_BY_USER_ID_AND_PASSWORD = "SELECT EXISTS (SELECT * FROM webdb.users WHERE user_id = ? AND password=?) AS exist";
    private static final String SQL_UPDATE_AMOUNT_FOR_VALUE_BY_ID = "UPDATE webdb.users SET amount=amount+? WHERE user_id=?";

    private static final String COLUMN_NAME_USER_ID = "user_id";
    private static final String COLUMN_NAME_USER_NAME = "name";
    private static final String COLUMN_NAME_USER_SURNAME = "surname";
    private static final String COLUMN_NAME_USER_ROLE = "role";
    private static final String COLUMN_NAME_USER_AMOUNT = "amount";
    private static final String COLUMN_NAME_PASSWORD = "password";
    private static final String COLUMN_NAME_EXIST = "exist";

    private static final UserDao instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDao getInstance() {
        return instance;
    }

    @Override
    public void add(User user, String encryptPassword) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_USERS)) {
            String name = user.getName();
            String surname = user.getSurname();
            String email = user.getEmail();
            int roleId = user.getRole().ordinal();
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, email);
            statement.setString(4, encryptPassword);
            statement.setInt(5, ++roleId);
            statement.execute();
            logger.log(Level.DEBUG, "Adding to the database");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateUserByPassword(long userId, String newPassword) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            statement.executeUpdate();
            logger.log(Level.DEBUG, "Update user by id");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findByEmailPassword(String email, String encryptPassword) throws DaoException {
        User user = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, encryptPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long userId = resultSet.getLong(COLUMN_NAME_USER_ID);
                String name = resultSet.getString(COLUMN_NAME_USER_NAME);
                String surname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                User.UserRole role = User.UserRole.valueOf(resultSet.getString(COLUMN_NAME_USER_ROLE).toUpperCase());
                BigDecimal amount = resultSet.getBigDecimal(COLUMN_NAME_USER_AMOUNT);
                user = new User(userId, name, surname, email, role, amount);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<String> findPasswordById(long id) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PASSWORD_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            String result = null;
            if (resultSet.next()) {
                result = resultSet.getString(COLUMN_NAME_PASSWORD);
            }
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findByRole(User.UserRole role) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_ROLE)) {
            preparedStatement.setString(1, role.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                long userId = resultSet.getLong(COLUMN_NAME_USER_ID);
                String name = resultSet.getString(COLUMN_NAME_USER_NAME);
                String surname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                User user = new User(userId, name, surname);
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean existByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            boolean result = !resultSet.getBoolean(COLUMN_NAME_EXIST);
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean existByPasswordAndUserId(long userId, String password) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_BY_USER_ID_AND_PASSWORD)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            boolean result = resultSet.getBoolean(COLUMN_NAME_EXIST);
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void orderDrugByCustomerIdAndPharmacistId(long customerId, long pharmacistId, BigDecimal value) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SQL_UPDATE_AMOUNT_FOR_VALUE_BY_ID);
            preparedStatement.setBigDecimal(1, value.multiply(BigDecimal.valueOf(-1)));
            preparedStatement.setLong(2, customerId);
            preparedStatement.executeUpdate();
            preparedStatement.setBigDecimal(1, value);
            preparedStatement.setLong(2, pharmacistId);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Rollback failed");
            }
            throw new DaoException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "AutoCommit change failed");
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception closing statement ");
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception closing connection");
            }

        }
    }
}


