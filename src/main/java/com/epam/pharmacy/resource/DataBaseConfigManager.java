package com.epam.pharmacy.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Class for reading properties for a database.
 *
 * @author Yauheni Tsitou.
 */
public class DataBaseConfigManager {

    /**
     * Logger for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Properties file containing database connection parameters
     */
    private static final Properties properties = new Properties();

    /**
     * The path to the properties file containing the database connection config.
     */
    private static final String PROPERTIES_PATH = "/dataBaseConfig.properties";

    static {
        try {
            properties.load(DataBaseConfigManager.class.getResourceAsStream(PROPERTIES_PATH));
        } catch (IOException e) {
            logger.log(Level.FATAL, "Database config not read", e);
            throw new RuntimeException("Database config not read", e);
        }
    }

    private DataBaseConfigManager() {
    }

    /**
     * Method for getting a property from a file.
     *
     * @param key Property key string.
     * @return Database property string.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
