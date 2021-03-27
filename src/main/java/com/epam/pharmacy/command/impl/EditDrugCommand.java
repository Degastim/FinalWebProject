package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class EditDrugCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int drugId = Integer.parseInt(request.getParameter(RequestParameter.DRUG_ID));
        String drugName = request.getParameter(RequestParameter.DRUG_NAME);
        BigDecimal drugAmount = BigDecimal.valueOf(Long.parseLong(request.getParameter(RequestParameter.DRUG_AMOUNT)));
        String drugDescription = request.getParameter(RequestParameter.DRUG_DESCRIPTION);
        String dosageString = request.getParameter(RequestParameter.DOSAGE);
        String priceString = request.getParameter(RequestParameter.PRICE);
        int dosage = Integer.parseInt(dosageString);
        int price = Integer.parseInt(priceString);
        boolean needPrescription = request.getParameter(RequestParameter.NEED_PRESCRIPTION).equals("true");
        try {
            drugService.updateDrug(drugId, drugName, drugAmount, drugDescription, needPrescription, dosage, price);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
