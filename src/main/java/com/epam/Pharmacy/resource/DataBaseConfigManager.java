package com.epam.Pharmacy.resource;

import java.util.ResourceBundle;

public class DataBaseConfigManager {
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("/dataBaseConfig");

    private DataBaseConfigManager() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
