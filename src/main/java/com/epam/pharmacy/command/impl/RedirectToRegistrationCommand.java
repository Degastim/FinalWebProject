package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class RedirectToRegistrationCommand implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        CommandResult commandResult=new CommandResult(PagePath.REGISTRATION_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
