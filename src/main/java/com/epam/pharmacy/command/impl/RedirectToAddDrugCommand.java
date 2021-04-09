package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandAccessLevel;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Action command to go to the page for adding a drug
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.PHARMACIST)
public class RedirectToAddDrugCommand implements ActionCommand {

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult(PagePath.ADD_DRUG_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
