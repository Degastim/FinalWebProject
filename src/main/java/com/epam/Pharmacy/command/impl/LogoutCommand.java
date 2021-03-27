package com.epam.Pharmacy.command.impl;

import com.epam.Pharmacy.command.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        HttpSession session=request.getSession();
        logger.log(Level.INFO, "Log out");
        session.removeAttribute(SessionAttribute.USER);
        CommandResult commandResult=new CommandResult(PagePath.INDEX_PAGE, CommandResult.Type.FORWARD);
        return commandResult;
    }
}
