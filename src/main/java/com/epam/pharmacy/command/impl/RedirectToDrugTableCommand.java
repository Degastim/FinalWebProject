package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CommandAccessLevel(User.Role.PHARMACIST)
public class RedirectToDrugTableCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<Drug> drugList = drugService.findAllDrugs();
            request.setAttribute(RequestParameter.DRUG_LIST, drugList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(PagePath.PHARMACIST_DRUG_TABLE_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
