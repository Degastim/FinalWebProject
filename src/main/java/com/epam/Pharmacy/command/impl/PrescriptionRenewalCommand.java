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

public class PrescriptionRenewalCommand implements ActionCommand {
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        long prescriptionId = Long.parseLong(request.getParameter(RequestParameter.PRESCRIPTION_ID));
        try {
            prescriptionService.updateStatusById(Prescription.Status.PROCESSING, prescriptionId);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
