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
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements ActionCommand {
    private static final UserService userService = UserServiceImpl.getInstance();

    private static final String REQUEST_ATTRIBUTE_PASSWORD_CHANGE_RESULT = "passwordChangeResult";
    private static final String MESSAGE_KEY_SUCCESSFUL_PASSWORD_CHANGE = "changePassword.message.successfulPasswordChange";
    private static final String MESSAGE_KEY_UNSUCCESSFUL_PASSWORD_CHANGE = "changePassword.message.unsuccessfulPasswordChange";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String newPassword = request.getParameter(RequestParameter.NEW_PASSWORD);
        String oldPassword = request.getParameter(RequestParameter.OLD_PASSWORD);
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long userId = user.getId();
        try {
            if (userService.updateByPassword(userId, newPassword, oldPassword)) {
                session.setAttribute(REQUEST_ATTRIBUTE_PASSWORD_CHANGE_RESULT, MessageManager.getMessage(MESSAGE_KEY_SUCCESSFUL_PASSWORD_CHANGE, (String) session.getAttribute(SessionAttribute.LOCALE)));
            } else {
                session.setAttribute(REQUEST_ATTRIBUTE_PASSWORD_CHANGE_RESULT, MessageManager.getMessage(MESSAGE_KEY_UNSUCCESSFUL_PASSWORD_CHANGE, (String) session.getAttribute(SessionAttribute.LOCALE)));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
