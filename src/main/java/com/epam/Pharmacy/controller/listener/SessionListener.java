package com.epam.Pharmacy.controller.listener;

import com.epam.Pharmacy.command.SessionAttribute;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "ru";
    private static final int DEFAULT_CURRENT_PAGINATION_PAGE = 1;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.LOCALE, DEFAULT_LOCALE);
        session.setAttribute(SessionAttribute.CURRENT_PAGINATION_PAGE, DEFAULT_CURRENT_PAGINATION_PAGE);
    }
}
