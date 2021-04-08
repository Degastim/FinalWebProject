package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandAccessLevel;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.model.entity.User;

import javax.servlet.http.HttpServletRequest;

@CommandAccessLevel({User.Role.CUSTOMER, User.Role.PHARMACIST})
public class RedirectToAccountReplenishment implements ActionCommand {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String page = PagePath.ACCOUNT_REPLENISHMENT_PAGE;
        CommandResult commandResult = new CommandResult(page, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
