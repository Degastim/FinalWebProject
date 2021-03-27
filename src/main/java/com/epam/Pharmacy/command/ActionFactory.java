package com.epam.Pharmacy.command;

import com.epam.Pharmacy.command.impl.EmptyCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final Logger logger = LogManager.getLogger();

    public static ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter(RequestParameter.COMMAND);
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARN, "Unsupported command");
        }
        return current;
    }
}
