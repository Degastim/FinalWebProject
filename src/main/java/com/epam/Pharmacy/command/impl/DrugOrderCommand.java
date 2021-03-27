package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Drug;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.DrugOrderService;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.PrescriptionService;
import com.epam.Pharmacy.model.service.impl.DrugOrderServiceImpl;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.Pharmacy.model.service.impl.PrescriptionServiceImpl;
import com.epam.Pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class DrugOrderCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();
    private static final DrugOrderService drugOrderService = DrugOrderServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String KEY_MESSAGE_ERROR_NO_DRUG = "drugOrderForm.error.noDrug";
    private static final String KEY_MESSAGE_ERROR_NO_PRESCRIPTION = "drugOrderForm.error.noPrescription";
    private static final String KEY_MESSAGE_ERROR_NO_CUSTOMER = "drugOrderForm.error.noCustomer";
    private static final String KEY_MESSAGE_ERROR_NO_DRUG_AMOUNT = "drugOrderForm.error.noDrugAmount";
    private static final String KEY_MESSAGE_ERROR_NO_USER_AMOUNT = "drugOrderForm.error.noUserAmount";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        CommandResult commandResult;
        String drugName = request.getParameter(RequestParameter.DRUG_NAME);
        String drugAmountString = request.getParameter(RequestParameter.DRUG_AMOUNT);
        int drugAmount = Integer.parseInt(drugAmountString);
        String dosageString = request.getParameter(RequestParameter.DOSAGE);
        int dosage = Integer.parseInt(dosageString);
        User customer = (User) session.getAttribute(SessionAttribute.USER);
        if (customer == null || customer.getRole() != User.UserRole.CUSTOMER) {
            session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, MessageManager.getMessage(KEY_MESSAGE_ERROR_NO_CUSTOMER, locale));
            commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
            return commandResult;
        }
        try {
            Optional<Drug> drugOptional = drugService.findDrugByDrugNameAndDosage(drugName, dosage);

            if (drugOptional.isEmpty()) {
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, MessageManager.getMessage(KEY_MESSAGE_ERROR_NO_DRUG, locale));
                commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
                return commandResult;
            }
            Drug drug = drugOptional.get();
            long customerId = customer.getUserId();
            boolean checkPrescriptionResult = prescriptionService.checkPrescription(customerId, drugName);
            if (!checkPrescriptionResult) {
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, MessageManager.getMessage(KEY_MESSAGE_ERROR_NO_PRESCRIPTION, locale));
                commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
                return commandResult;
            }
            if (drug.getAmount() < drugAmount) {
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, MessageManager.getMessage(KEY_MESSAGE_ERROR_NO_DRUG_AMOUNT, locale));
                commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
                return commandResult;
            }
            boolean drugOrderResult = drugOrderService.orderPayment(customer, drug, drugAmount);
            if (!drugOrderResult) {
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, MessageManager.getMessage(KEY_MESSAGE_ERROR_NO_USER_AMOUNT, locale));
                commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
                return commandResult;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
