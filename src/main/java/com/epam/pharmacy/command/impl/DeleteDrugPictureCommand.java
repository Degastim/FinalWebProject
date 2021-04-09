package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.ActionCommand;
import com.epam.pharmacy.command.CommandAccessLevel;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestParameter;
import com.epam.pharmacy.exception.CommandException;
import com.epam.pharmacy.exception.ServiceException;
import com.epam.pharmacy.model.entity.User;
import com.epam.pharmacy.model.service.DrugPictureService;
import com.epam.pharmacy.model.service.impl.DrugPictureServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Action command for deleting a picture of a drug from the database
 *
 * @author Yauheni Tsitou
 */
@CommandAccessLevel(User.Role.PHARMACIST)
public class DeleteDrugPictureCommand implements ActionCommand {
    private static final DrugPictureService drugPictureService = DrugPictureServiceImpl.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String drugPictureIdString = request.getParameter(RequestParameter.DRUG_PICTURE_ID);
        long drugPictureId = Long.parseLong(drugPictureIdString);
        try {
            drugPictureService.delete(drugPictureId);
            CommandResult commandResult = new CommandResult(CommandResult.Type.RETURN_CURRENT_PAGE_WITH_REDIRECT);
            return commandResult;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
