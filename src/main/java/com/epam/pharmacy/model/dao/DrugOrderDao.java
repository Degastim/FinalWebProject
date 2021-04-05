package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.Order;
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

public class DrugOrderDao extends AbstractDao<Order> {
    private static final Logger logger = LogManager.getLogger();
    private static final DrugOrderDao instance = new DrugOrderDao();

    private DrugOrderDao() {
    }

    public static DrugOrderDao getInstance() {
        return instance;
    }

    private static final String SQL_ADD_ORDER = "INSERT INTO webdb.drug_orders(customer_id, drug_order_drug_id,drugs_number,drug_order_status_id) VALUES(?,?,?,?)";
    private static final String SQL_FIND_BY_CUSTOMER_ID = "SELECT drug_order_id,customer_id, name, surname, email, role, users.amount, drug_order_drug_id, drug_name, drug_amount, description, need_prescription, dosage, price, drugs_number, drug_order_status FROM webdb.drug_orders JOIN webdb.drug_order_statuses ON drug_orders.drug_order_status_id=drug_order_statuses.drug_order_status_id JOIN webdb.users ON customer_id=user_id JOIN webdb.roles ON users.role_id=roles.role_id JOIN webdb.drugs ON drug_orders.drug_order_drug_id=drugs.drug_id WHERE customer_id=?";
    private static final String SQL_SELECT_BY_STATUS = "SELECT drug_order_id,customer_id, name, surname, email, role, users.amount, drug_order_drug_id, drug_name, drug_amount, description, need_prescription, dosage, price, drugs_number, drug_order_status FROM webdb.drug_orders JOIN webdb.drug_order_statuses ON drug_orders.drug_order_status_id=drug_order_statuses.drug_order_status_id JOIN webdb.users ON customer_id=user_id JOIN webdb.roles ON users.role_id=roles.role_id JOIN webdb.drugs ON drug_orders.drug_order_drug_id=drugs.drug_id WHERE drug_orders.drug_order_status_id=?";
    private static final String SQL_UPDATE_STATUS_BY_ID = "UPDATE webdb.drug_orders SET drug_order_status_id=? WHERE drug_order_id=?";
    private static final String SQL_FIND_BY_ID = "SELECT drug_order_id,customer_id, name, surname, email, role, users.amount, drug_order_drug_id, drug_name, drug_amount, description, need_prescription, dosage, price, drugs_number, drug_order_status FROM webdb.drug_orders JOIN webdb.drug_order_statuses ON drug_orders.drug_order_status_id=drug_order_statuses.drug_order_status_id JOIN webdb.users ON customer_id=user_id JOIN webdb.roles ON users.role_id=roles.role_id JOIN webdb.drugs ON drug_orders.drug_order_drug_id=drugs.drug_id WHERE drug_orders.drug_order_id=?";

    private static final String COLUMN_NAME_DRUG_ORDER_ID = "drug_order_id";
    private static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_NAME_USER_NAME = "name";
    private static final String COLUMN_NAME_USER_SURNAME = "surname";
    private static final String COLUMN_NAME_USER_EMAIL = "email";
    private static final String COLUMN_NAME_ROLE = "role";
    private static final String COLUMN_NAME_USER_AMOUNT = "amount";
    private static final String COLUMN_NAME_DRUG_ORDER_DRUG_ID = "drug_order_drug_id";
    private static final String COLUMN_NAME_DRUG_NAME = "drug_name";
    private static final String COLUMN_NAME_DRUG_AMOUNT = "drug_amount";
    private static final String COLUMN_NAME_DESCRIPTION = "description";
    private static final String COLUMN_NAME_NEED_PRESCRIPTION = "need_prescription";
    private static final String COLUMN_NAME_DOSAGE = "dosage";
    private static final String COLUMN_NAME_PRICE = "price";
    private static final String COLUMN_NAME_DRUGS_NUMBER = "drugs_number";
    private static final String COLUMN_NAME_DRUG_ORDER_STATUS = "drug_order_status";

    @Override
    public void add(Order order) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_ORDER)) {
            User customer = order.getCustomer();
            long customerId = customer.getId();
            Drug drug = order.getDrug();
            long drugId = drug.getId();
            int amount = order.getDrugsNumber();
            Order.Status status = order.getStatus();
            long statusId = status.ordinal() + 1;
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, drugId);
            preparedStatement.setInt(3, amount);
            preparedStatement.setLong(4, statusId);
            preparedStatement.execute();
            logger.log(Level.INFO, "Adding a new drug order");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Order> findByCustomerId(long customerId) throws DaoException {
        List<Order> drugOrderList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_CUSTOMER_ID)) {
            preparedStatement.setLong(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long drugOrderId = resultSet.getLong(COLUMN_NAME_DRUG_ORDER_ID);
                String customerName = resultSet.getString(COLUMN_NAME_USER_NAME);
                String customerSurname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                String email = resultSet.getString(COLUMN_NAME_USER_EMAIL);
                String roleString = resultSet.getString(COLUMN_NAME_ROLE);
                User.Role role = User.Role.valueOf(roleString.toUpperCase());
                BigDecimal customerAmount = resultSet.getBigDecimal(COLUMN_NAME_USER_AMOUNT);
                User customer = new User(customerId, customerName, customerSurname, email, role, customerAmount);
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ORDER_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DESCRIPTION);
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DOSAGE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                Drug drug = new Drug(drugId, drugName, drugAmount, description, needPrescription, dosage, price);
                int drugsNumber = resultSet.getInt(COLUMN_NAME_DRUGS_NUMBER);
                String drugOrderStatusString = resultSet.getString(COLUMN_NAME_DRUG_ORDER_STATUS);
                Order.Status drugOrderStatus = Order.Status.valueOf(drugOrderStatusString.toUpperCase());
                Order drugOrder = new Order(drugOrderId, customer, drug, drugsNumber, drugOrderStatus);
                drugOrderList.add(drugOrder);
            }
            return drugOrderList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Order> findByStatus(Order.Status status) throws DaoException {
        List<Order> drugOrderList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_STATUS)) {
            long statusId = status.ordinal() + 1;
            preparedStatement.setLong(1, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long drugOrderId = resultSet.getLong(COLUMN_NAME_DRUG_ORDER_ID);
                long customerId = resultSet.getLong(COLUMN_NAME_CUSTOMER_ID);
                String customerName = resultSet.getString(COLUMN_NAME_USER_NAME);
                String customerSurname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                String email = resultSet.getString(COLUMN_NAME_USER_EMAIL);
                String roleString = resultSet.getString(COLUMN_NAME_ROLE);
                User.Role role = User.Role.valueOf(roleString.toUpperCase());
                BigDecimal customerAmount = resultSet.getBigDecimal(COLUMN_NAME_USER_AMOUNT);
                User customer = new User(customerId, customerName, customerSurname, email, role, customerAmount);
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ORDER_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DESCRIPTION);
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DOSAGE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                Drug drug = new Drug(drugId, drugName, drugAmount, description, needPrescription, dosage, price);
                int drugsNumber = resultSet.getInt(COLUMN_NAME_DRUGS_NUMBER);
                String drugOrderStatusString = resultSet.getString(COLUMN_NAME_DRUG_ORDER_STATUS);
                Order.Status drugOrderStatus = Order.Status.valueOf(drugOrderStatusString.toUpperCase());
                Order drugOrder = new Order(drugOrderId, customer, drug, drugsNumber, drugOrderStatus);
                drugOrderList.add(drugOrder);
            }
            return drugOrderList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void updateStatusById(long drugOrderId, long statusId) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS_BY_ID)) {
            preparedStatement.setLong(1, statusId);
            preparedStatement.setLong(2, drugOrderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Order> findById(long drugOrderId) throws DaoException {
        Order drugOrder = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {
            preparedStatement.setLong(1, drugOrderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long customerId = resultSet.getLong(COLUMN_NAME_CUSTOMER_ID);
                String customerName = resultSet.getString(COLUMN_NAME_USER_NAME);
                String customerSurname = resultSet.getString(COLUMN_NAME_USER_SURNAME);
                String email = resultSet.getString(COLUMN_NAME_USER_EMAIL);
                String roleString = resultSet.getString(COLUMN_NAME_ROLE);
                User.Role role = User.Role.valueOf(roleString.toUpperCase());
                BigDecimal customerAmount = resultSet.getBigDecimal(COLUMN_NAME_USER_AMOUNT);
                User customer = new User(customerId, customerName, customerSurname, email, role, customerAmount);
                long drugId = resultSet.getLong(COLUMN_NAME_DRUG_ORDER_DRUG_ID);
                String drugName = resultSet.getString(COLUMN_NAME_DRUG_NAME);
                int drugAmount = resultSet.getInt(COLUMN_NAME_DRUG_AMOUNT);
                String description = resultSet.getString(COLUMN_NAME_DESCRIPTION);
                boolean needPrescription = resultSet.getBoolean(COLUMN_NAME_NEED_PRESCRIPTION);
                int dosage = resultSet.getInt(COLUMN_NAME_DOSAGE);
                BigDecimal price = resultSet.getBigDecimal(COLUMN_NAME_PRICE);
                Drug drug = new Drug(drugId, drugName, drugAmount, description, needPrescription, dosage, price);
                int drugsNumber = resultSet.getInt(COLUMN_NAME_DRUGS_NUMBER);
                String drugOrderStatusString = resultSet.getString(COLUMN_NAME_DRUG_ORDER_STATUS);
                Order.Status drugOrderStatus = Order.Status.valueOf(drugOrderStatusString.toUpperCase());
                drugOrder = new Order(drugOrderId, customer, drug, drugsNumber, drugOrderStatus);
            }
            return Optional.ofNullable(drugOrder);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
