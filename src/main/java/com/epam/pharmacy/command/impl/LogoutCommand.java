package com.epam.pharmacy.command.impl;

import com.epam.pharmacy.command.*;
import com.epam.pharmacy.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CommandAccessLevel({User.Role.CUSTOMER, User.Role.DOCTOR, User.Role.PHARMACIST})
public class LogoutCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.log(Level.INFO, "Log out");
        session.removeAttribute(SessionAttribute.USER);
        CommandResult commandResult = new CommandResult(PagePath.INDEX_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
