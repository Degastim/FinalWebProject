package com.epam.pharmacy.resource;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class DataBaseConfigManager {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
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

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
