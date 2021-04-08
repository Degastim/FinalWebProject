package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
@CommandAccessLevel(User.Role.PHARMACIST)
public class EditDrugCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final String MESSAGE_KEY_ERROR_MESSAGE = "editDrug.error.drug";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int drugId = Integer.parseInt(request.getParameter(RequestParameter.DRUG_ID));
        String drugName = request.getParameter(RequestParameter.DRUG_NAME);
        String drugAmountString = request.getParameter(RequestParameter.DRUG_AMOUNT);
        String drugDescription = request.getParameter(RequestParameter.DRUG_DESCRIPTION);
        String dosageString = request.getParameter(RequestParameter.DOSAGE);
        String priceString = request.getParameter(RequestParameter.PRICE);
        String needPrescriptionString = request.getParameter(RequestParameter.NEED_PRESCRIPTION);
        int dosage = Integer.parseInt(dosageString);
        int drugAmount = Integer.parseInt(drugAmountString);
        BigDecimal price = new BigDecimal(priceString);
        boolean needPrescription = Boolean.parseBoolean(needPrescriptionString);
        try {
            boolean updateResult = drugService.updateDrug(drugId, drugName, drugAmount, drugDescription, needPrescription, price, dosage);
            if (!updateResult) {
                String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_MESSAGE, locale);
                session.setAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
