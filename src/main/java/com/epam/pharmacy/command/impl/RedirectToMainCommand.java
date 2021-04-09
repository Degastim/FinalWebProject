package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Drug;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Action command to go to the main page
 *
 * @author Yauheni Tsitou
 */
public class RedirectToMainCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_CARDS_DRUG_LIST = "cardsDrugList";
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
            request.setAttribute(REQUEST_ATTRIBUTE_CARDS_DRUG_LIST, drugList);
            int startPaginationPage = drugService.countStartPaginationPage(currentPaginationPage);
            request.setAttribute(REQUEST_ATTRIBUTE_START_PAGINATION_PAGE, startPaginationPage);
            int lastPaginationPage = drugService.countLastPaginationPage(currentPaginationPage);
            request.setAttribute(REQUEST_ATTRIBUTE_LAST_PAGINATION_PAGE, lastPaginationPage);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
