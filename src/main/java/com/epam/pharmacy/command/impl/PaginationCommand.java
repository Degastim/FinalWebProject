package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.command.SessionAttribute;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.service.DrugService;
import com.epam.pharmacy.model.service.impl.DrugServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Action command to navigate through the pages of the drug list
 *
 * @author Yauheni Tsitou
 */
public class PaginationCommand implements ActionCommand {
    private static final DrugService drugService = DrugServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String paginationPage = request.getParameter(RequestParameter.PAGINATION_PAGE);
        int currentPaginationPage = (int) session.getAttribute(SessionAttribute.CURRENT_PAGINATION_PAGE);
        try {
            currentPaginationPage = drugService.findCurrentPaginationPage(paginationPage, currentPaginationPage);
            session.setAttribute(SessionAttribute.CURRENT_PAGINATION_PAGE, currentPaginationPage);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
