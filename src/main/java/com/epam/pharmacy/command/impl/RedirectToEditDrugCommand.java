package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Action command to go to the page for editing drug parameters
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.PHARMACIST)
public class RedirectToEditDrugCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_EDIT_DRUG = "editDrug";
    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE_KEY_ERROR_MESSAGE = "editDrug.error.noDrug";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
        String drugIdString = request.getParameter(RequestParameter.DRUG_ID);
        long drugId = drugIdString != null ? Long.parseLong(drugIdString) : (int) session.getAttribute(RequestParameter.DRUG_ID);
        try {
            Optional<Drug> optionalDrug = drugService.findByIdWithImages(drugId);
            if (optionalDrug.isPresent()) {
                Drug drug = optionalDrug.get();
                drug.setId(drugId);
                request.setAttribute(REQUEST_ATTRIBUTE_EDIT_DRUG, drug);
            } else {
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_MESSAGE, locale);
                request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
            CommandResult commandResult = new CommandResult(PagePath.EDIT_DRUG_PAGE, CommandResult.Type.FORWARD);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
