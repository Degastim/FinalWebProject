package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.model.service.impl.UserServiceImpl;
import com.epam.pharmacy.util.resource.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Action command to confirm confirmation code
 *
 * @author Yauheni Tsitou
 */
public class CodeConfirmationCommand implements ActionCommand {
    /**
     * Logger for writing logs.
     */
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String MESSAGE_KEY_ERROR_MESSAGE = "errorMessage";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int verificationCode = Integer.parseInt(request.getParameter(RequestParameter.VERIFICATION_CODE));
        int emailVerificationCode = (int) session.getAttribute(SessionAttribute.VERIFICATION_CODE);
        CommandResult commandResult;
        if (verificationCode == emailVerificationCode) {
            User user = (User) session.getAttribute(SessionAttribute.POSSIBLE_USER);
            session.removeAttribute(SessionAttribute.POSSIBLE_USER);
            String password = (String) session.getAttribute(SessionAttribute.PASSWORD);
            try {
                userService.add(user, password);
                session.setAttribute(SessionAttribute.USER, user);
                session.removeAttribute(SessionAttribute.POSSIBLE_USER);
                commandResult = new CommandResult(PagePath.START_PAGE, CommandResult.Type.FORWARD);
                logger.log(Level.INFO, "Successful registration");
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
            String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_MESSAGE, locale);
            session.setAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage);
            commandResult = new CommandResult(PagePath.CODE_CONFIRMATION_PAGE, CommandResult.Type.FORWARD);
            logger.log(Level.INFO, "Incorrect verification code entered ");
        }
        return commandResult;
    }
}
