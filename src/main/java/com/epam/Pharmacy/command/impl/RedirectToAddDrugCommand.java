package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.PagePath;
import com.epam.Pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class RedirectToAddDrugCommand implements ActionCommand {

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult(PagePath.ADD_DRUG_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
