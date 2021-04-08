package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.model.entity.User;

import javax.servlet.http.HttpServletRequest;

@CommandAccessLevel(User.Role.CUSTOMER)
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
