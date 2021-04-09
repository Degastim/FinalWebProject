package com.epam.pharmacy.util.mail;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * Class for sending messages to email.
 *
 * @author Yauheni Tsitou.
 */
public class MailSender {

    /**
     * Logger for writing logs
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Contains properties {@link MailSender}.
     */
    private static final Properties properties;

    /**
     * The path to the properties file for the {@link MailSender}.
     */
    private static final String MAIL_PROPERTY_FILE_PATH = "mail.properties";

    /**
     * MIME_type of message to send to mail.
     */
    private static final String MIME_TYPE = "text/html";

    static {
        properties = new Properties();
        try {
            properties.load(MailSender.class.getClassLoader().getResourceAsStream(MAIL_PROPERTY_FILE_PATH));
        } catch (IOException e) {
            logger.log(Level.ERROR, "Can't read:" + MAIL_PROPERTY_FILE_PATH);
        }
    }

    /**
     * Address where to send the message.
     */
    private final String sendToEmail;

    /**
     * The headline of the message.
     */
    private final String mailSubject;

    /**
     * Message text.
     */
    private final String mailText;

    /**
     * The message to be sent to the email.
     */
    private MimeMessage message;

    public MailSender(String sendToEmail, String mailSubject, String mailText) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
    }

    /**
     * Method for sending a message to the mail.
     */
    public void send() {
        try {
            initMessage();
            Transport.send(message);
        } catch (AddressException e) {
            logger.log(Level.ERROR, "Invalid address: " + sendToEmail + " " + e);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, "Error generating or sending message: " + e);
        }
    }


    /**
     * Message creation method.
     *
     * @throws MessagingException
     */
    private void initMessage() throws MessagingException {
        Session mailSession = SessionFactory.createSession(properties);
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        message.setSubject(mailSubject);
        message.setContent(mailText, MIME_TYPE);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
    }
}