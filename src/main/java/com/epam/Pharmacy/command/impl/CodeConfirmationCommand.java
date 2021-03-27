package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.*;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.UserService;
import com.epam.Pharmacy.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CodeConfirmationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int verificationCode = Integer.parseInt(request.getParameter(RequestParameter.VERIFICATION_CODE));
        int emailVerificationCode = (int) session.getAttribute(SessionAttribute.VERIFICATION_CODE);
        CommandResult commandResult;
        if (verificationCode == emailVerificationCode) {
            User user = (User) session.getAttribute(SessionAttribute.POSSIBLE_USER);
            String password = (String) session.getAttribute(SessionAttribute.PASSWORD);
            try {
                userService.add(user, password);
                session.setAttribute(SessionAttribute.USER, user);
                session.removeAttribute(SessionAttribute.POSSIBLE_USER);
                commandResult = new CommandResult(PagePath.START_PAGE, CommandResult.Type.FORWARD);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
            logger.log(Level.INFO, "Successful registration");
        } else {
            commandResult = new CommandResult(PagePath.CODE_CONFIRMATION_PAGE, CommandResult.Type.FORWARD);
        }
        return commandResult;
    }
}
