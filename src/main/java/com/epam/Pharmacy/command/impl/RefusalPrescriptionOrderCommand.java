package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Prescription;
import com.epam.Pharmacy.model.service.PrescriptionService;
import com.epam.Pharmacy.model.service.impl.PrescriptionServiceImpl;

import javax.servlet.http.HttpServletRequest;

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
