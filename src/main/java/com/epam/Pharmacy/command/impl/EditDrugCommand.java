package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;

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
