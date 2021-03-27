package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class RedirectToEditDrugCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_EDIT_DRUG = "editDrug";
    private static final String REQUEST_ATTRIBUTE_ERROR = "error";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int drugId = request.getParameter(RequestParameter.DRUG_ID) != null ? Integer.parseInt(request.getParameter(RequestParameter.DRUG_ID)) : (int) session.getAttribute(RequestParameter.DRUG_ID);
        try {
            Optional<Drug> optionalDrug = drugService.findByIdWithImages(drugId);
            if (optionalDrug.isPresent()) {
                Drug drug = optionalDrug.get();
                drug.setDrugId(drugId);
                request.setAttribute(REQUEST_ATTRIBUTE_EDIT_DRUG, drug);
            } else {
                request.setAttribute(REQUEST_ATTRIBUTE_ERROR, "No drug found in the database ");
            }
            session.setAttribute(RequestParameter.DRUG_ID, drugId);
            CommandResult commandResult = new CommandResult(PagePath.EDIT_DRUG_PAGE, CommandResult.Type.FORWARD);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
