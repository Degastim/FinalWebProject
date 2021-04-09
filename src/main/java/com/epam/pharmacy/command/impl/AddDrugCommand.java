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

/**
 * Action command to add a drug
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.PHARMACIST)
public class AddDrugCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();


    private static final String MESSAGE_KEY_ERROR_WRONG_DRUG = "addDrug.error.drug";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String drugName = request.getParameter(RequestParameter.DRUG_NAME);
        String drugAmountString = request.getParameter(RequestParameter.DRUG_AMOUNT);
        String dosageString = request.getParameter(RequestParameter.DOSAGE);
        String priceString = request.getParameter(RequestParameter.PRICE);
        String drugDescription = request.getParameter(RequestParameter.DRUG_DESCRIPTION);
        String needPrescriptionString = request.getParameter(RequestParameter.NEED_PRESCRIPTION);
        boolean needPrescription = Boolean.parseBoolean(needPrescriptionString);
        int dosage = Integer.parseInt(dosageString);
        BigDecimal price = new BigDecimal(priceString);
        int drugAmount = Integer.parseInt(drugAmountString);
        try {
            boolean additionResult = drugService.add(drugName, drugAmount, drugDescription, needPrescription, dosage, price);
            if (!additionResult) {
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_WRONG_DRUG, locale);
                session.setAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
