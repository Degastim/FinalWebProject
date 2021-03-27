package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.PagePath;
import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.entity.Drug;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.Pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RedirectToMainCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_CARDS_DRUG_LIST = "cardsDrugList";
    private static final String REQUEST_ATTRIBUTE_PAGINATION_PAGE_AMOUNT = "paginationPageAmount";
    private static final String REQUEST_ATTRIBUTE_START_PAGINATION_PAGE = "startPaginationPage";
    private static final String REQUEST_ATTRIBUTE_LAST_PAGINATION_PAGE = "lastPaginationPage";
    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";
    private static final String KEY_ERROR_MESSAGE = "main.errorMessage.noDrug";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int currentPaginationPage = (int) session.getAttribute(SessionAttribute.CURRENT_PAGINATION_PAGE);
        CommandResult commandResult = new CommandResult(PagePath.MAIN_PAGE, CommandResult.Type.FORWARD);
        try {
            List<Drug> drugList = drugService.findPaginationDrugs(currentPaginationPage);
            if (drugList.size() == 0) {
                String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
                String errorMessage = MessageManager.getMessage(KEY_ERROR_MESSAGE, locale);
                request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, errorMessage);
                return commandResult;
            }
            int paginationPageAmount = drugService.countPaginationPageAmount();
            request.setAttribute(REQUEST_ATTRIBUTE_CARDS_DRUG_LIST, drugList);
            request.setAttribute(REQUEST_ATTRIBUTE_PAGINATION_PAGE_AMOUNT, paginationPageAmount);
            int startPaginationPage = drugService.countStartPaginationPage(currentPaginationPage);
            request.setAttribute(REQUEST_ATTRIBUTE_START_PAGINATION_PAGE, startPaginationPage);
            int lastPaginationPage = drugService.countLastPaginationPage(currentPaginationPage, paginationPageAmount);
            request.setAttribute(REQUEST_ATTRIBUTE_LAST_PAGINATION_PAGE, lastPaginationPage);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
