package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.PagePath;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Drug;
import com.epam.Pharmacy.model.entity.User;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.UserService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.Pharmacy.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RedirectToAddPrescription implements ActionCommand {
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final DrugService drugService = DrugServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<User> doctorList = userService.findByRole(User.UserRole.DOCTOR);
            List<Drug> drugList = drugService.findDrugNameAndIdWithNeedPrescription(true);
            request.setAttribute("doctorList", doctorList);
            request.setAttribute("drugList", drugList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(PagePath.ADD_PRESCRIPTION_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
