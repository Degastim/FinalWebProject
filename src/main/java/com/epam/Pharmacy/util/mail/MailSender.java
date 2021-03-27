package com.epam.Pharmacy.util.mail;

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

public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties;
    private static final String MAIL_PROPERTY_FILE_PATH = "mail.properties";

    static {
        properties = new Properties();
        try {
            properties.load(MailSender.class.getClassLoader().getResourceAsStream(MAIL_PROPERTY_FILE_PATH));
        } catch (IOException e) {
            logger.log(Level.ERROR, "Can't read:" + MAIL_PROPERTY_FILE_PATH);
        }
    }

    private final String sendToEmail;
    private final String mailSubject;
    private final String mailText;
    private MimeMessage message;

    public MailSender(String sendToEmail, String mailSubject, String mailText) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
    }

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

    private void initMessage() throws MessagingException {
        Session mailSession = SessionFactory.createSession(properties);
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        message.setSubject(mailSubject);
        message.setContent(mailText, "text/html");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
    }
}