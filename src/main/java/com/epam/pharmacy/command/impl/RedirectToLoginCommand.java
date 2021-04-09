package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;

import javax.servlet.http.HttpServletRequest;

/**
 * Action command to go to login page
 *
 * @author Yauheni Tsitou
 */
public class RedirectToLoginCommand implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult(PagePath.LOGIN_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
