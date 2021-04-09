package com.epam.pharmacy.util.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Class for creating a mail session.
 *
 * @author Yauheni Tsitou.
 */
public class SessionFactory {
    private static final String MAIL_USER_FIELD = "mail.user.name";
    private static final String MAIL_USER_PASSWORD_FIELD = "mail.user.password";

    /**
     * Method for creating a mail session.
     *
     * @param configProperties properties containing a property to create a mail session.
     * @return mail session.
     */
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
