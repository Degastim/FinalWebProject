package com.epam.Pharmacy.model.dao.impl;

import com.epam.Pharmacy.exception.DaoException;
import com.epam.Pharmacy.model.dao.DrugOrderDao;
import com.epam.Pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DrugOrderDaoImpl implements DrugOrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final DrugOrderDao instance = new DrugOrderDaoImpl();

    private DrugOrderDaoImpl() {
    }

    public static DrugOrderDao getInstance() {
        return instance;
    }

    private static final String SQL_ADD_ORDER = "INSERT INTO webdb.orders(customer_id, order_drug_id,amount) VALUES(?,?,?)";

    @Override
    public void add(long customerId, int drugId, int amount) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_ORDER)) {
            preparedStatement.setLong(1, customerId);
            preparedStatement.setInt(2, drugId);
            preparedStatement.setInt(3, amount);
            preparedStatement.execute();
            logger.log(Level.INFO, "Adding a new drug order");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
