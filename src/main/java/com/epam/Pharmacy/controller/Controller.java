package com.epam.Pharmacy.controller;

import com.epam.Pharmacy.command.*;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ConnectionPoolException;
import com.epam.Pharmacy.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CommandResult commandResult;
        String page;
        try {
            ActionCommand command = ActionFactory.defineCommand(request);
            commandResult = command.execute(request);
            page = commandResult.providePath();
            switch (commandResult.getType()) {
                case FORWARD: {
                    session.setAttribute(SessionAttribute.PREVIOUS_URL, request.getHeader(RequestParameter.HEADER_REFERER));
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                    dispatcher.forward(request, response);
                    logger.log(Level.INFO, "Forwarding to another jsp");
                    break;
                }
                case RETURN_CURRENT_PAGE_WITH_REDIRECT: {
                    String previousUrl = request.getHeader(RequestParameter.HEADER_REFERER);
                    response.sendRedirect(previousUrl);
                    logger.log(Level.INFO, "Redirecting to to current page");
                    break;
                }
                case REDIRECT: {
                    response.sendRedirect(page);
                    logger.log(Level.INFO, "Redirecting to another jsp");
                    break;
                }
            }
            logger.log(Level.INFO, command + " command executed ");
        } catch (CommandException e) {
            response.sendRedirect(request.getContextPath() + PagePath.ERROR_PAGE);
            logger.log(Level.ERROR, "Redirecting to the error page", e);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.INSTANCE.destroyPool();
            logger.log(Level.INFO,"Destroy pool");
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "ConnectionPool closing error", e);
        }
    }
}