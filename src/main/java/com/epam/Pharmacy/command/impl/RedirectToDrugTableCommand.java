package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.PagePath;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Drug;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        CommandResult commandResult = new CommandResult(PagePath.DRUG_LIST_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
