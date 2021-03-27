package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.PagePath;

import javax.servlet.http.HttpServletRequest;

public class RedirectToLoginCommand implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult(PagePath.LOGIN_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
