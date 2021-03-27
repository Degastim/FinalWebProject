package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
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
