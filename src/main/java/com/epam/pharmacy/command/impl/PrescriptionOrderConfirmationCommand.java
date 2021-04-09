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
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Action command to confirm prescription order by doctor
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.DOCTOR)
public class PrescriptionOrderConfirmationCommand implements ActionCommand {
    private static final TimeService timeService = TimeServiceImpl.getInstance();
    private static final PrescriptionService prescriptionService = PrescriptionServiceImpl.getInstance();
    private static final String MESSAGE_KEY_DAY_ERROR_MESSAGE = "prescriptionOrder.errorMessage.day";
    private static final String SEPARATOR = "?";
    private static final String COMMAND_REDIRECT = "command=redirect_to_prescription_order_table";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        CommandResult commandResult;
        String dayString = request.getParameter(RequestParameter.DAY);
        String monthString = request.getParameter(RequestParameter.MONTH);
        String yearString = request.getParameter(RequestParameter.YEAR);
        String prescriptionIdString = request.getParameter(RequestParameter.PRESCRIPTION_ID);
        int day = Integer.parseInt(dayString);
        int month = Integer.parseInt(monthString);
        int year = Integer.parseInt(yearString);
        long prescriptionId = Long.parseLong(prescriptionIdString);
        boolean isDayInMonth = timeService.isDayInMonth(day, month, year);
        if (!isDayInMonth) {
            String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
            String errorMessage = MessageManager.getMessage(MESSAGE_KEY_DAY_ERROR_MESSAGE, locale);
            session.setAttribute(SessionAttribute.ERROR_MESSAGE, errorMessage);
            commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
            return commandResult;
        }
        long currentTime = timeService.findCurrentTime();
        long endTime = timeService.findTimeByDayAndMonthAndYear(day, month, year);
        try {
            prescriptionService.updateIssueDateAndEndDateAndStatusById(prescriptionId, currentTime, endTime, Prescription.Status.APPROVED);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        commandResult = new CommandResult(request.getRequestURL() + SEPARATOR + COMMAND_REDIRECT, CommandResult.Type.REDIRECT);
        return commandResult;
    }
}
