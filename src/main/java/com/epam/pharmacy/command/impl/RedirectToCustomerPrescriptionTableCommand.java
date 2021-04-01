package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.model.service.impl.PrescriptionServiceImpl;
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RedirectToCustomerPrescriptionTableCommand implements ActionCommand {
    private static final String REQUEST_ATTRIBUTE_PRESCRIPTION_LIST = "prescriptionList";
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();
    private static final String MESSAGE_KEY_ERROR_MESSAGE_NO_PRESCRIPTION = "customerPrescriptionTable.error.noPrescription";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long customerId = user.getUserId();
        try {
            List<Prescription> prescriptionList = prescriptionService.findAllByCustomerIdWithDoctorNameAndDoctorSurnameAndDrugName(customerId);
            if (prescriptionList.size() == 0) {
                String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_MESSAGE_NO_PRESCRIPTION, locale);
                request.setAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage);
            }
            request.setAttribute(REQUEST_ATTRIBUTE_PRESCRIPTION_LIST, prescriptionList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(PagePath.PRESCRIPTION_TABLE_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
