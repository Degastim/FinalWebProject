package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandAccessLevel;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.pharmacy.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Action command to go to the page for adding an order for a drug
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.CUSTOMER)
public class RedirectToAddPrescriptionOrder implements ActionCommand {
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final DrugService drugService = DrugServiceImpl.getInstance();

    private static final String REQUEST_ATTRIBUTE_DOCTOR_LIST = "doctorList";
    private static final String REQUEST_ATTRIBUTE_DRUG_LIST = "drugList";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<User> doctorList = userService.findByRole(User.Role.DOCTOR);
            List<Drug> drugList = drugService.findDrugByNeedPrescription(true);
            request.setAttribute(REQUEST_ATTRIBUTE_DOCTOR_LIST, doctorList);
            request.setAttribute(REQUEST_ATTRIBUTE_DRUG_LIST, drugList);
            CommandResult commandResult = new CommandResult(PagePath.ADD_PRESCRIPTION_ORDER_PAGE, CommandResult.Type.FORWARD);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
