package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;

import javax.servlet.http.HttpServletRequest;

/**
 * Empty action command
 *
 * @author Yauheni Tsitou
 */
public class EmptyCommand implements ActionCommand {
    public CommandResult execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult(PagePath.INDEX_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
