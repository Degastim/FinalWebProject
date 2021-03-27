package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Prescription;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.PrescriptionService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.Pharmacy.model.service.impl.PrescriptionServiceImpl;
import com.epam.Pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CreatePrescriptionRequestCommand implements ActionCommand {
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();
    private static final DrugService drugService = DrugServiceImpl.getInstance();

    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE = "addPrescriptionErrorMessage";
    private static final String ERROR_MESSAGE_KEY = "customerPrescriptionTable.errorMessage";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        CommandResult commandResult;
        long doctorId = Long.parseLong(request.getParameter(RequestParameter.DOCTOR));
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long customerId = user.getUserId();
        String drugName = request.getParameter(RequestParameter.DRUG_NAME);
        int drugAmount = Integer.parseInt(request.getParameter(RequestParameter.DRUG_AMOUNT));
        try {
            boolean comparisonResult = drugService.checkNeedPrescriptionByDrugName(drugName, true);
            if (comparisonResult) {
                int drugId = drugService.findDrugIdByDrugName(drugName);
                prescriptionService.createPrescriptionByDoctorIdAndCustomerIdAndDrugNameAndAmountAndStatus(customerId, doctorId, drugId, drugAmount, Prescription.Status.PROCESSING);
            } else {
                String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, MessageManager.getMessage(ERROR_MESSAGE_KEY, locale));
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
