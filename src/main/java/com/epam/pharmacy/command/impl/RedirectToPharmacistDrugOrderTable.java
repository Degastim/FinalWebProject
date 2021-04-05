package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.PagePath;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.Order;
import com.epam.pharmacy.model.service.DrugOrderService;
import com.epam.pharmacy.model.service.impl.DrugOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RedirectToPharmacistDrugOrderTable implements ActionCommand {
    private static final DrugOrderService drugOrderService = DrugOrderServiceImpl.getInstance();
    private static final String REQUEST_ATTRIBUTE_DRUG_ORDER_LIST = "drugOrderList";

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<Order> drugOrderList = drugOrderService.findByStatus(Order.Status.PROCESSING);
            request.setAttribute(REQUEST_ATTRIBUTE_DRUG_ORDER_LIST, drugOrderList);
            String page = PagePath.PHARMACIST_DRUG_ORDER_TABLE_PAGE;
            CommandResult commandResult = new CommandResult(page, CommandResult.Type.FORWARD);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

    }
}
