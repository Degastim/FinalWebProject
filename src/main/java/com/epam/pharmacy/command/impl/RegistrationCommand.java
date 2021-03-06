package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.model.service.impl.UserServiceImpl;
import com.epam.pharmacy.util.resource.MessageManager;
import com.epam.pharmacy.util.Encrypter;
import com.epam.pharmacy.util.Randomizer;
import com.epam.pharmacy.util.mail.MailSender;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Action command for registration
 *
 * @author Yauheni Tsitou
 */
public class RegistrationCommand implements ActionCommand {
    /**
     * Logger for writing logs.
     */
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE_KEY_ERROR_FORM = "registration.error.form";
    private static final String MESSAGE_KEY_ERROR_ROLE = "registration.error.role";
    private static final String MAIL_SUBJECT = "Verification code";
    private static final String REDIRECT_COMMAND = "command=redirect_to_code_confirmation";
    private static final String SEPARATOR = "?";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String name = request.getParameter(RequestParameter.USER_NAME);
        String surname = request.getParameter(RequestParameter.USER_SURNAME);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String email = request.getParameter(RequestParameter.USER_EMAIL);
        String roleString = request.getParameter(RequestParameter.USER_ROLE).toUpperCase();
        User.Role role = User.Role.valueOf(roleString);
        CommandResult commandResult;
        try {
            if (role == User.Role.PHARMACIST) {
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_FORM, locale);
                session.setAttribute(MESSAGE_KEY_ERROR_ROLE, errorMessage);
                commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
                return commandResult;
            }
            if (userService.checkRegistrationForm(name, surname, password, email)) {
                int verificationCode = Randomizer.random(4);
                session.setAttribute(SessionAttribute.VERIFICATION_CODE, verificationCode);
                User user = new User(name, surname, email, role);
                session.setAttribute(SessionAttribute.POSSIBLE_USER, user);
                String encryptPassword=Encrypter.encrypt(password);
                session.setAttribute(SessionAttribute.PASSWORD, encryptPassword);
                MailSender mailSender = new MailSender(email, MAIL_SUBJECT, String.valueOf(verificationCode));
                mailSender.send();
                commandResult = new CommandResult(request.getRequestURL() + SEPARATOR + REDIRECT_COMMAND, CommandResult.Type.REDIRECT);
            } else {
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_FORM, locale);
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
                logger.log(Level.INFO, "Unsuccessful registration");
            }
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
