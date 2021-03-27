package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.PagePath;
import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Prescription;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.PrescriptionService;
import com.epam.Pharmacy.model.service.impl.PrescriptionServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RedirectToCustomerPrescriptionTableCommand implements ActionCommand {
    private static final String REQUEST_ATTRIBUTE_PRESCRIPTION_LIST = "prescriptionList";
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long customerId = user.getUserId();
        try {
            List<Prescription> prescriptionList = prescriptionService.findAllByCustomerIdWithDoctorNameSurnameAndDrugNameWithoutPrescriptionId(customerId);
            request.setAttribute(REQUEST_ATTRIBUTE_PRESCRIPTION_LIST, prescriptionList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(PagePath.PRESCRIPTION_TABLE_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
