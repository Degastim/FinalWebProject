package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.*;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.UserService;
import com.epam.Pharmacy.model.service.impl.UserServiceImpl;
import com.epam.Pharmacy.resource.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_ERROR_LOGIN_PASS_MESSAGE = "errorLoginPassMessage";
    private static final String MESSAGE_KEY_LOGIN_ERROR = "message.loginError";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String email = request.getParameter(RequestParameter.USER_EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        CommandResult commandResult;
        try {
            Optional<User> optional = userService.findByEmailPassword(email, password);
            if (optional.isPresent()) {
                User user = optional.get();
                session.setAttribute(SessionAttribute.USER, user);
                commandResult = new CommandResult(PagePath.START_PAGE, CommandResult.Type.FORWARD);
                logger.log(Level.INFO, "Successful login");
            } else {
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_LOGIN_PASS_MESSAGE, MessageManager.getMessage(MESSAGE_KEY_LOGIN_ERROR, (String) session.getAttribute(SessionAttribute.LOCALE)));
                commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
                logger.log(Level.INFO, "Unsuccessful login");
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return commandResult;
    }
}
