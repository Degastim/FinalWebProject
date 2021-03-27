package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;

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
