package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Action command to go to the start page
 *
 * @author Yauheni Tsitou
 */
public class RedirectToStartCommand implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult(PagePath.START_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
