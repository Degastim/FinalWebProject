package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.PagePath;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Drug;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;

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
