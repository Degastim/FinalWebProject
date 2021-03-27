package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.*;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.UserService;
import com.epam.Pharmacy.model.service.impl.UserServiceImpl;
import com.epam.Pharmacy.resource.MessageManager;
import com.epam.Pharmacy.util.Randomizer;
import com.epam.Pharmacy.util.mail.MailSender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_REGISTRATION_ERROR_MESSAGE = "registrationErrorMessage";
    private static final String ERROR_MESSAGE_KEY = "registration.message.registrationError";
    private static final String MAIL_SUBJECT = "Verification code";
    private static final String REDIRECT_COMMAND="command=redirect_to_code_confirmation";
    private static final String SEPARATOR="?";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String name = request.getParameter(RequestParameter.USER_NAME);
        String surname = request.getParameter(RequestParameter.USER_SURNAME);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String email = request.getParameter(RequestParameter.USER_EMAIL);
        User.UserRole role = User.UserRole.valueOf(request.getParameter(RequestParameter.USER_ROLE).toUpperCase());
        CommandResult commandResult;
        try {
            if (userService.checkRegistrationForm(name, surname, password, email)) {
                int verificationCode = Randomizer.random(4);
                session.setAttribute(SessionAttribute.VERIFICATION_CODE, verificationCode);
                User user = new User(name, surname, email, role);
                session.setAttribute(SessionAttribute.POSSIBLE_USER, user);
                session.setAttribute(SessionAttribute.PASSWORD, password);
                MailSender mailSender = new MailSender(email, MAIL_SUBJECT, String.valueOf(verificationCode));
                mailSender.send();
                commandResult=new CommandResult(request.getRequestURL()+SEPARATOR+REDIRECT_COMMAND, CommandResult.Type.REDIRECT);
            } else {
                session.setAttribute(REQUEST_ATTRIBUTE_REGISTRATION_ERROR_MESSAGE, MessageManager.getMessage(ERROR_MESSAGE_KEY, (String) session.getAttribute(SessionAttribute.LOCALE)));
                commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
                logger.log(Level.INFO, "Unsuccessful registration");
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
