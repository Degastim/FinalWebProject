package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.model.service.impl.PrescriptionServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action command to go to the customer's prescription page
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.CUSTOMER)
public class RedirectToCustomerPrescriptionTableCommand implements ActionCommand {
    private static final String REQUEST_ATTRIBUTE_PRESCRIPTION_LIST = "prescriptionList";
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        long customerId = user.getId();
        try {
            List<Prescription> prescriptionList = prescriptionService.findAllByCustomerId(customerId);
            request.setAttribute(REQUEST_ATTRIBUTE_PRESCRIPTION_LIST, prescriptionList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(PagePath.PRESCRIPTION_TABLE_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
