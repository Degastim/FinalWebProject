package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class DeleteDrugCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final String REDIRECT_URL_PARAMETER = "?command=redirect_to_drug_table";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int drugId = Integer.parseInt(request.getParameter(RequestParameter.DRUG_ID));
        try {
            drugService.deleteById(drugId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(request.getRequestURL() + REDIRECT_URL_PARAMETER, CommandResult.Type.REDIRECT);
        return commandResult;
    }
}
