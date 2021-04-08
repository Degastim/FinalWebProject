package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugOrderService;
import com.epam.pharmacy.model.service.impl.DrugOrderServiceImpl;
import com.epam.pharmacy.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CommandAccessLevel(User.Role.PHARMACIST)
public class RefuseDrugOrder implements ActionCommand {
    private static final DrugOrderService drugOrderService = DrugOrderServiceImpl.getInstance();
    private static final String MESSAGE_KEY_ERROR_MESSAGE_NO_DRUG_ORDER = "pharmacist_drug_order_table.error.noDrugOrder";
    private static final String REQUEST_ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String drugOrderIdString = request.getParameter(RequestParameter.DRUG_ORDER_ID);
        long drugOrderId = Long.parseLong(drugOrderIdString);
        try {
            boolean refuseOrderResult = drugOrderService.refuseDrugOrderService(drugOrderId);
            if (!refuseOrderResult) {
                String locale = (String) session.getAttribute(SessionAttribute.LOCALE);
                String errorMessage = MessageManager.getMessage(MESSAGE_KEY_ERROR_MESSAGE_NO_DRUG_ORDER, locale);
                session.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, errorMessage);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
        return commandResult;
    }
}
