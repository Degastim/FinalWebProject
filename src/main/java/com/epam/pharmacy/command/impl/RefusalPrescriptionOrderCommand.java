package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandAccessLevel;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Prescription;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.PrescriptionService;
import com.epam.pharmacy.model.service.impl.PrescriptionServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Action command for refusing to issue a prescription to a customer
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.DOCTOR)
public class RefusalPrescriptionOrderCommand implements ActionCommand {
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String prescriptionIdString = request.getParameter(RequestParameter.PRESCRIPTION_ID);
        long prescriptionId = Long.parseLong(prescriptionIdString);
        try {
            prescriptionService.updateStatusById(Prescription.Status.REJECTED, prescriptionId);
            CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
