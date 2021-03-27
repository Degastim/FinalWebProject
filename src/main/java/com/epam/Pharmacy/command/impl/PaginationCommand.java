package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.ActionCommand;
import com.epam.Pharmacy.command.CommandResult;
import com.epam.Pharmacy.command.RequestParameter;
import com.epam.Pharmacy.command.SessionAttribute;
import com.epam.Pharmacy.exception.CommandException;
import com.epam.Pharmacy.exception.ServiceException;
import com.epam.Pharmacy.model.service.DrugService;
import com.epam.Pharmacy.model.service.impl.DrugServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
