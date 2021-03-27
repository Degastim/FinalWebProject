package com.epam.Pharmacy.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {
    private MessageManager() {
    }

    public static String getMessage(String key, String value) {
        String language = "";
        if (!value.equals("en")) {
            language = value;
        }
        Locale locale = new Locale(language);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("localization/messages", locale);
        return resourceBundle.getString(key);
    }
}
