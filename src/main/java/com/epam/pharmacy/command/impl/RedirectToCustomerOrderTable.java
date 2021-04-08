package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugOrderService;
import com.epam.pharmacy.model.service.impl.DrugOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@CommandAccessLevel(User.Role.CUSTOMER)
public class RedirectToCustomerOrderTable implements ActionCommand {
    private static final DrugOrderService drugOrderService = DrugOrderServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_DRUG_ORDER_LIST = "drugOrderList";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User customer = (User) session.getAttribute(SessionAttribute.USER);
        long customerId = customer.getId();
        try {
            List<Order> drugOrderList = drugOrderService.findByCustomerId(customerId);
            request.setAttribute(REQUEST_ATTRIBUTE_DRUG_ORDER_LIST, drugOrderList);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        String page = PagePath.CUSTOMER_DRUG_ORDER_TABLE_PAGE;
        CommandResult commandResult = new CommandResult(page, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
