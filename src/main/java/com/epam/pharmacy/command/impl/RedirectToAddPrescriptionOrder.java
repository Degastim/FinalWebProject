package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.UserService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.pharmacy.model.service.impl.UserServiceImpl;
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RedirectToAddPrescriptionOrder implements ActionCommand {
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final DrugService drugService = DrugServiceImpl.getInstance();

    private static final String REQUEST_ATTRIBUTE_DOCTOR_LIST = "doctorList";
    private static final String REQUEST_ATTRIBUTE_DRUG_LIST = "drugList";
    private static final String MESSAGE_KEY_ERROR_NO_DOCTOR="addPrescriptionOrder.error.noDoctor";
    private static final String MESSAGE_KEY_ERROR_NO_DRUG="addPrescriptionOrder.error.noDrug";
    private static final String REQUEST_ATTRIBUTE_NO_DOCTOR_MESSAGE="noDoctorMessage";
    private static final String REQUEST_ATTRIBUTE_NO_DRUG_MESSAGE="noDrugMessage";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session=request.getSession();
        String locale= (String) session.getAttribute(SessionAttribute.LOCALE);
        try {
            List<User> doctorList = userService.findByRole(User.Role.DOCTOR);
            List<Drug> drugList = drugService.findDrugByNeedPrescription(true);
            if(doctorList.size()==0){
                String noDoctorMessage= MessageManager.getMessage(MESSAGE_KEY_ERROR_NO_DOCTOR,locale);
                request.setAttribute(REQUEST_ATTRIBUTE_NO_DOCTOR_MESSAGE,noDoctorMessage);
            }
            if(drugList.size()==0){
                String noDrugMessage=MessageManager.getMessage(MESSAGE_KEY_ERROR_NO_DRUG,locale);
                request.setAttribute(REQUEST_ATTRIBUTE_NO_DRUG_MESSAGE,noDrugMessage);
            }
            request.setAttribute(REQUEST_ATTRIBUTE_DOCTOR_LIST, doctorList);
            request.setAttribute(REQUEST_ATTRIBUTE_DRUG_LIST, drugList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(PagePath.ADD_PRESCRIPTION_ORDER_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
