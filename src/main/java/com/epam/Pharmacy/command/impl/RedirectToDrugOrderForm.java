package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.PagePath;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class RedirectToDrugOrderForm implements ActionCommand {
    private static final String REQUEST_ATTRIBUTE_DRUG_NAME = "drugName";
    private static final String REQUEST_ATTRIBUTE_DOSAGE = "dosage";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String drugName = request.getParameter(RequestParameter.DRUG_NAME);
        String dosage = request.getParameter(RequestParameter.DOSAGE);
        request.setAttribute(REQUEST_ATTRIBUTE_DOSAGE, dosage);
        request.setAttribute(REQUEST_ATTRIBUTE_DRUG_NAME, drugName);
        CommandResult commandResult = new CommandResult(PagePath.DRUG_ORDER_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
