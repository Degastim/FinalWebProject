package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.*;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Prescription;
import com.epam.Pharmacy.model.service.PrescriptionService;
import com.epam.Pharmacy.model.service.TimeService;
import com.epam.Pharmacy.model.service.impl.PrescriptionServiceImpl;
import com.epam.Pharmacy.model.service.impl.TimeServiceImpl;
import com.epam.Pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class RedirectToPrescriptionOrder implements ActionCommand {
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();
    private static final TimeService timeService = TimeServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_PRESCRIPTION = "prescription";
    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String REQUEST_ATTRIBUTE_CURRENT_YEAR = "currentYear";
    private static final String REQUEST_ATTRIBUTE_MAX_YEAR = "maxYear";
    private static final String MESSAGE_KEY_ERROR_MESSAGE = "prescriptionOrder.errorMessage.empty";
    private static final int NUMBER_YEARS_HIGHER_PRESENT = 4;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int prescriptionId = Integer.parseInt(request.getParameter(RequestParameter.PRESCRIPTION_ID));
        request.setAttribute(RequestParameter.PRESCRIPTION_ID, prescriptionId);
        try {
            Optional<Prescription> prescriptionOptional = prescriptionService.findPrescriptionByIdWithoutDoctor(prescriptionId);
            if (prescriptionOptional.isPresent()) {
                Prescription prescription=prescriptionOptional.get();
                request.setAttribute(REQUEST_ATTRIBUTE_PRESCRIPTION, prescription);
                int currentYear = timeService.findCurrentYear();
                request.setAttribute(REQUEST_ATTRIBUTE_CURRENT_YEAR, currentYear);
                int maxYear = currentYear + NUMBER_YEARS_HIGHER_PRESENT;
                request.setAttribute(REQUEST_ATTRIBUTE_MAX_YEAR, maxYear);
            } else {
                String errorMessage=MessageManager.getMessage(MESSAGE_KEY_ERROR_MESSAGE, (String) session.getAttribute(SessionAttribute.LOCALE));
                request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE,errorMessage);
            }
            CommandResult commandResult = new CommandResult(PagePath.PRESCRIPTION_ORDER_PAGE, CommandResult.Type.FORWARD);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }


    }
}
