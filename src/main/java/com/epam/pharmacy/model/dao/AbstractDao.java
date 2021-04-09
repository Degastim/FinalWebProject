package com.epam.pharmacy.model.dao;

import com.epam.pharmacy.exception.DaoException;
import com.epam.pharmacy.model.entity.Entity;

import java.sql.Connection;

/**
 * Class {@code AbstractDao} is the root of the dao class hierarchy.
 *
 * @param <T> class inherited from class Entity
 * @author Yauheni Tsitou
 */
public abstract class AbstractDao<T extends Entity> {
    /**
     * The value is used to store the connection to the database.
     */
    protected Connection connection;

    /**
     * Method for adding entity to database
     *
     * @param entity
     * @throws DaoException Bug throwing on an error in a dao layer
     */
    public abstract void add(T entity) throws DaoException;

    /**
     * Database connection method
     *
     * @param connection Database connections
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

