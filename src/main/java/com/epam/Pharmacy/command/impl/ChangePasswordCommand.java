package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.*;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.UserService;
import com.epam.Pharmacy.model.service.impl.UserServiceImpl;
import com.epam.Pharmacy.resource.MessageManager;

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
        try {
            if (userService.updateByPassword(user.getUserId(), newPassword, oldPassword)) {
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
