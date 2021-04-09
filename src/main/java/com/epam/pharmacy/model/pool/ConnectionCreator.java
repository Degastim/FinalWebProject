package com.epam.pharmacy.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class used to create Connection objects.
 *
 * @author Yauheni Tsitou.
 */
class ConnectionCreator {

    /**
     * Logger for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Contains properties {@link ConnectionCreator}.
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * String value containing the path to the properties file
     */
    private static final String PROPERTIES_PATH = "/dataBaseConfig.properties";

    /**
     * String value containing driver key for registration.
     */
    private static final String DRIVER_PROPERTY = "db.driver";
    /**
     * String value containing url key to get connection.
     */
    private static final String URL_PROPERTY = "db.url";

    static {
        try {
            PROPERTIES.load(ConnectionCreator.class.getResourceAsStream(PROPERTIES_PATH));
            String driver = PROPERTIES.getProperty(DRIVER_PROPERTY);
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "MySQL drives not found", e);
            throw new RuntimeException("MySQL drives not found", e);
        } catch (IOException e) {
            logger.log(Level.FATAL, "Database configuration not found.Path" + PROPERTIES_PATH, e);
            throw new RuntimeException("Database configuration not found.Path" + PROPERTIES_PATH, e);
        }
    }

    private ConnectionCreator() {
    }

    /**
     * Creates a Connection object.
     *
     * @return Connection object.
     * @throws SQLException if a database access error occurs or the url is null.
     */
    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTIES.getProperty(URL_PROPERTY), PROPERTIES);
    }
}
