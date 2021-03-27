package com.epam.Pharmacy.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionCreator {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties PROPERTIES = new Properties();

    private static final String PROPERTIES_PATH = "/dataBaseConfig.properties";
    private static final String DRIVER_PROPERTY = "db.driver";
    private static final String URL_PROPERTY = "db.url";

    static {
        try {
            PROPERTIES.load(ConnectionCreator.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH));
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

    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTIES.getProperty(URL_PROPERTY), PROPERTIES);
    }
}
