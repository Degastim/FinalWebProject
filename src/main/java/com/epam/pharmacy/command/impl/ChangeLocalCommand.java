package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Action command to change language
 *
 * @author Yauheni Tsitou
 */
public class ChangeLocalCommand implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String newLocale = request.getParameter(RequestParameter.LOCALE);
        session.setAttribute(SessionAttribute.LOCALE, newLocale);
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
