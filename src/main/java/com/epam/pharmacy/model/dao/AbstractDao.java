package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Entity;
import com.epam.pharmacy.model.entity.Order;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> {
    private static final Logger logger = LogManager.getLogger();
    protected Connection connection;
    public abstract void add(T entity) throws DaoException;


    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Exception closing statement");
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

