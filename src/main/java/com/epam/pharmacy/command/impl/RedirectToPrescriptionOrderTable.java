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
 * Action command to go to the page with the table of orders for reps for the doctor
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.DOCTOR)
public class RedirectToPrescriptionOrderTable implements ActionCommand {
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();

    private static final String REQUEST_ATTRIBUTE_PRESCRIPTION_LIST = "prescriptionList";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User doctor = (User) session.getAttribute(SessionAttribute.USER);
        long doctorId = doctor.getId();
        try {
            List<Prescription> prescriptionList = prescriptionService.findAllByDoctorIdAndStatus(doctorId, Prescription.Status.PROCESSING);
            request.setAttribute(REQUEST_ATTRIBUTE_PRESCRIPTION_LIST, prescriptionList);
            CommandResult commandResult = new CommandResult(PagePath.PRESCRIPTION_ORDER_TABLE_PAGE, CommandResult.Type.FORWARD);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
