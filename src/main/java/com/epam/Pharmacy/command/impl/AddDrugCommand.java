package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.Pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddDrugCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final String MESSAGE_KEY_ERROR_WRONG_AMOUNT = "addDrug.error.wrongAmount";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String drugName = request.getParameter(RequestParameter.DRUG_NAME);
        String drugAmountString = request.getParameter(RequestParameter.DRUG_AMOUNT);
        int drugAmount = Integer.parseInt(drugAmountString);
        if (drugAmount < 0) {
            String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_WRONG_AMOUNT, locale);
            session.setAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage);
        }
        String drugDescription = request.getParameter(RequestParameter.DRUG_DESCRIPTION);
        boolean needPrescription = request.getParameter(RequestParameter.NEED_PRESCRIPTION).equals("true");
        try {
            drugService.add(drugName, drugAmount, drugDescription, needPrescription);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}