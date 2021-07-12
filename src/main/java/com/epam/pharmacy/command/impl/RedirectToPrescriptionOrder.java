package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.model.service.TimeService;
import com.epam.pharmacy.model.service.impl.PrescriptionServiceImpl;
import com.epam.pharmacy.model.service.impl.TimeServiceImpl;
import com.epam.pharmacy.util.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Action command to go to the prescription order page
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.DOCTOR)
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
        String prescriptionIdString = request.getParameter(RequestParameter.PRESCRIPTION_ID);
        long prescriptionId = Integer.parseInt(prescriptionIdString);
        try {
            Optional<Prescription> prescriptionOptional = prescriptionService.findPrescriptionById(prescriptionId);
            if (prescriptionOptional.isPresent()) {
                Prescription prescription = prescriptionOptional.get();
                request.setAttribute(REQUEST_ATTRIBUTE_PRESCRIPTION, prescription);
                int currentYear = timeService.findCurrentYear();
                request.setAttribute(REQUEST_ATTRIBUTE_CURRENT_YEAR, currentYear);
                int maxYear = currentYear + NUMBER_YEARS_HIGHER_PRESENT;
                request.setAttribute(REQUEST_ATTRIBUTE_MAX_YEAR, maxYear);
            } else {
                String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_MESSAGE, locale);
                request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
            CommandResult commandResult = new CommandResult(PagePath.PRESCRIPTION_ORDER_PAGE, CommandResult.Type.FORWARD);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
