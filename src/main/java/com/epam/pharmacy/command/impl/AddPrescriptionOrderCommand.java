package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.pharmacy.model.service.impl.PrescriptionServiceImpl;
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@CommandAccessLevel(User.Role.CUSTOMER)
public class AddPrescriptionOrderCommand implements ActionCommand {
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();
    private static final DrugService drugService = DrugServiceImpl.getInstance();

    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_MESSAGE_KEY = "customerPrescriptionTable.errorMessage";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        CommandResult commandResult;
        String doctorIdString = request.getParameter(RequestParameter.DOCTOR);
        String drugName = request.getParameter(RequestParameter.DRUG_NAME);
        String drugAmountString = request.getParameter(RequestParameter.DRUG_AMOUNT);
        String dosageString = request.getParameter(RequestParameter.DOSAGE);
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long doctorId = Long.parseLong(doctorIdString);
        long customerId = user.getId();
        int drugAmount = Integer.parseInt(drugAmountString);
        int dosage = Integer.parseInt(dosageString);
        try {
            int drugId = 0;
            boolean comparisonResult = drugService.checkNeedPrescriptionByDrugNameAndDosage(drugName, dosage, true);
            Optional<Integer> drugIdOptional = drugService.findDrugIdByDrugNameAndDosage(drugName, dosage);
            if (drugIdOptional.isEmpty()) {
                comparisonResult = false;
            } else {
                drugId = drugIdOptional.get();
            }
            if (comparisonResult) {
                prescriptionService.add(customerId, doctorId, drugId, drugAmount, Prescription.Status.PROCESSING);
            } else {
                String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
                String errorMessage = MessageManager.getMessage(ERROR_MESSAGE_KEY, locale);
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
