package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The class that initializes the objects passed to it DAO
 */
public class EntityTransaction {
    /**
     * Logger for writing logs.
     */
    private static final Logger logger = LogManager.getLogger();
    private Connection connection;

    /**
     * Internalize a database connection by taking connections from the connection pool.Changes the value of autocommit.
     *
     * @param dao  {@link AbstractDao} object to which the connection is issued.
     * @param daos {@link AbstractDao} objects to which the connection is issued.
     */
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

    /**
     * Changes the value of autocommit. And closes the database connection.
     */
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

    /**
     * Makes all changes made since the previous commit/rollback permanent and releases any database locks currently held by this Connection object.
     */
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

    /**
     * Internalize a database connection by taking connections from the connection pool
     *
     * @param dao {@link AbstractDao} object to which the connection is issued.
     */
    public void init(AbstractDao dao) {
        if (connection == null) {
            connection = ConnectionPool.INSTANCE.getConnection();
        }
        dao.setConnection(connection);
    }

    /**
     * Closes the database connection.
     */
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
