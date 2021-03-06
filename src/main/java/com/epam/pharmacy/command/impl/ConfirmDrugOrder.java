package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandAccessLevel;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.DrugOrder;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugOrderService;
import com.epam.pharmacy.model.service.impl.DrugOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Action command to confirm order for a drug from a pharmacist
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.PHARMACIST)
public class ConfirmDrugOrder implements ActionCommand {
    private static final DrugOrderService drugOrderService = DrugOrderServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String drugOrderIdString = request.getParameter(RequestParameter.DRUG_ORDER_ID);
        long drugOrderId = Long.parseLong(drugOrderIdString);
        try {
            drugOrderService.updateStatusById(drugOrderId, DrugOrder.Status.APPROVED);
            CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
