package com.epam.pharmacy.model.pool;

import com.epam.pharmacy.exception.ConnectionPoolException;
import com.epam.pharmacy.resource.DataBaseConfigManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public enum ConnectionPool {

    INSTANCE;

    private final Logger logger = LogManager.getLogger();
    private static final String DATA_BASE_POOL_SIZE_CONFIG = "db.poolSize";
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> busyConnections;
    private static final int DEFAULT_POOL_SIZE = 8;
    private int poolSize;

    ConnectionPool() {
        try {
            poolSize = Integer.parseInt(DataBaseConfigManager.getProperty(DATA_BASE_POOL_SIZE_CONFIG));
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            poolSize = DEFAULT_POOL_SIZE;
        }
        freeConnections = new LinkedBlockingDeque<>(poolSize);
        busyConnections = new ArrayDeque<>(poolSize);
        try {
            for (int i = 0; i < poolSize; i++) {
                Connection connection = ConnectionCreator.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
            }
        } catch (SQLException e) {
            logger.log(Level.FATAL, "Error creating connection pool", e);
            throw new RuntimeException("Error creating connection pool", e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            busyConnections.remove(proxyConnection);
            freeConnections.offer(proxyConnection);
            logger.log(Level.DEBUG, "Connection has been released");
        } else {
            logger.log(Level.ERROR, "Invalid connection to release");
        }

    }

    public void destroyPool() throws ConnectionPoolException {
        for (int i = 0; i < poolSize; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException e) {
                throw new ConnectionPoolException(e);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, e);
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, e);
            }
        });
    }
}
