package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    /**
     * Logger for writing logs.
     */
    private static final Logger logger = LogManager.getLogger();
    private Connection connection;

    public void initTransaction(AbstractDao dao, AbstractDao... daos) {
        if (connection == null) {
            connection = ConnectionPool.INSTANCE.getConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "AutoCommit change failed");
        }
        dao.setConnection(connection);
        logger.log(Level.INFO, "Connection issued " + dao);
        for (AbstractDao daoElement : daos) {
            daoElement.setConnection(connection);
            logger.log(Level.INFO, "Connection issued " + daoElement);
        }
    }

    public void endTransaction() {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "AutoCommit change failed");
        }
        end();
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Commit failed");
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            logger.log(Level.ERROR, "Rollback failed");
        }
    }

    public void init(AbstractDao dao) {
        if (connection == null) {
            connection = ConnectionPool.INSTANCE.getConnection();
        }
        dao.setConnection(connection);
    }

    public void end() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception closing connection");
        }
        connection = null;
    }
}
