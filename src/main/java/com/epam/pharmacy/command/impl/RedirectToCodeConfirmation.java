package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class RedirectToCodeConfirmation implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult(PagePath.CODE_CONFIRMATION_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
