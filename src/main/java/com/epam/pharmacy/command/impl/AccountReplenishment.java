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
import java.math.BigDecimal;

public class AccountReplenishment implements ActionCommand {
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String MESSAGE_KEY_SUCCESS_MESSAGE = "account_replenishment.message.success";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String amountString = request.getParameter(RequestParameter.AMOUNT);
        BigDecimal amount = new BigDecimal(amountString);
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            userService.updateByAmount(user, amount);
            String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
            String informationMessage = MessageManager.getMessage(MESSAGE_KEY_SUCCESS_MESSAGE, locale);
            session.setAttribute(SessionAttribute.INFORMATION_MESSAGE, informationMessage);
            CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
