package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao extends AbstractDao<User> {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_ADD_USER = "INSERT INTO webdb.users(name, surname, email, role_id, amount) VALUES (?,?,?,?,?)";
    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT user_id, users.name, users.surname,roles.role, users.amount FROM webdb.users JOIN webdb.roles ON users.role_id=roles.role_id  WHERE email=?";
    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE webdb.users SET password=? WHERE user_id=?";
    private static final String SQL_FIND_USERS_BY_ROLE = "SELECT user_id, name,email, surname,amount FROM webdb.users LEFT JOIN webdb.roles ON users.role_id=roles.role_id WHERE role=?";
    private static final String SQL_EXIST_BY_EMAIL = "SELECT EXISTS (SELECT * FROM webdb.users WHERE email = ?) AS exist";
    private static final String SQL_EXIST_BY_USER_ID_AND_PASSWORD = "SELECT EXISTS (SELECT * FROM webdb.users WHERE user_id = ? AND password=?) AS exist";
    private static final String SQL_UPDATE_USER = "UPDATE webdb.users SET name=?,surname=?,email=?,role_id=?,amount=? WHERE user_id=?";
    private static final String SQL_FIND_BY_ID = "SELECT name,email, surname,amount,role FROM webdb.users LEFT JOIN webdb.roles ON users.role_id=roles.role_id WHERE user_id=?";
    private static final String SQL_FIND_PASSWORD_BY_EMAIL = "SELECT  password FROM webdb.users WHERE email=?";

    private static final String COLUMN_NAME_USER_ID = "user_id";
    private static final String COLUMN_NAME_USER_NAME = "name";
    private static final String COLUMN_NAME_USER_SURNAME = "surname";
    private static final String COLUMN_NAME_USER_ROLE = "role";
    private static final String COLUMN_NAME_USER_AMOUNT = "amount";
    private static final String COLUMN_NAME_EXIST = "exist";
    private static final String COLUMN_NAME_EMAIL = "email";
    private static final String COLUMN_NAME_PASSWORD = "password";

    private static final UserDao instance = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return instance;
    }

    public void updateUserByPassword(long userId, String newPassword) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            statement.executeUpdate();
            logger.log(Level.DEBUG, "Update user by id");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findByEmail(String email) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                long userId = resultSet.getLong(COLUMN_NAME_USER_ID);
                String name = resultSet.getString(COLUMN_NAME_USER_NAME);
                String surname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                User.Role role = User.Role.valueOf(resultSet.getString(COLUMN_NAME_USER_ROLE).toUpperCase());
                BigDecimal amount = resultSet.getBigDecimal(COLUMN_NAME_USER_AMOUNT);
                user = new User(userId, name, surname, email, role, amount);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<User> findByRole(User.Role role) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USERS_BY_ROLE)) {
            preparedStatement.setString(1, role.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                long userId = resultSet.getLong(COLUMN_NAME_USER_ID);
                String name = resultSet.getString(COLUMN_NAME_USER_NAME);
                String surname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                String email = resultSet.getString(COLUMN_NAME_EMAIL);
                BigDecimal amount = resultSet.getBigDecimal(COLUMN_NAME_USER_AMOUNT);
                User user = new User(userId, name, surname, email, role, amount);
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean existByEmail(String email) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            boolean result = resultSet.getBoolean(COLUMN_NAME_EXIST);
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean existByPasswordAndUserId(long userId, String password) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_EXIST_BY_USER_ID_AND_PASSWORD)) {
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
    public void add(User user) throws DaoException {
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        User.Role role = user.getRole();
        int roleId = role.ordinal() + 1;
        BigDecimal amount = user.getAmount();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, roleId);
            preparedStatement.setBigDecimal(5, amount);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public void update(User user) throws DaoException {
        long userId = user.getId();
        String name = user.getName();
        String surname = user.getSurname();
        String email = user.getEmail();
        User.Role role = user.getRole();
        int roleId = role.ordinal() + 1;
        BigDecimal amount = user.getAmount();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, roleId);
            preparedStatement.setBigDecimal(5, amount);
            preparedStatement.setLong(6, userId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<User> findById(long userId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            User user = null;
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(COLUMN_NAME_USER_NAME);
                String surname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                String email = resultSet.getString(COLUMN_NAME_EMAIL);
                String roleString = resultSet.getString(COLUMN_NAME_USER_ROLE);
                User.Role role = User.Role.valueOf(roleString.toUpperCase());
                BigDecimal amount = resultSet.getBigDecimal(COLUMN_NAME_USER_AMOUNT);
                user = new User(userId, name, surname, email, role, amount);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<String> findPasswordByEmail(String email) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PASSWORD_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            String password = null;
            if (resultSet.next()) {
                password = resultSet.getString(COLUMN_NAME_PASSWORD);
            }
            return Optional.ofNullable(password);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}


