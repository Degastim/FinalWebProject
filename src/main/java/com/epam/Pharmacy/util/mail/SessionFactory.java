package com.epam.Pharmacy.util.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SessionFactory {
    private static final String MAIL_USER_FIELD = "mail.user.name";
    private static final String MAIL_USER_PASSWORD_FIELD = "mail.user.password";

    public static Session createSession(Properties configProperties) {
        String userName = configProperties.getProperty(MAIL_USER_FIELD);
        String userPassword = configProperties.getProperty(MAIL_USER_PASSWORD_FIELD);
        return Session.getDefaultInstance(configProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, userPassword);
            }
        });
    }
}
