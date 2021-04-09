package com.epam.pharmacy.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class on receiving a message depending on the language.
 *
 * @author Yauheni Tsitou.
 */
public class MessageManager {
    private MessageManager() {
    }

    /**
     * Method for receiving a message.
     *
     * @param key   Key string to access the message.
     * @param value String providing values to get {@code Locale}.
     * @return string-message with the required localization.
     */
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
