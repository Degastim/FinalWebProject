package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
