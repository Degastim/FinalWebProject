package com.epam.pharmacy.command;

import com.epam.pharmacy.command.impl.EmptyCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Factory issuing command by parameter value in request
 *
 * @author Yauheni Tsitou
 */
public class ActionFactory {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Defines an ActionCommand from the request.
     *
     * @param request HttpServletRequest object, which may contain the ActionCommand.
     * @return emptyCommand object if command is empty or not present in the request,
     * otherwise - Optional object of ActionCommand object.
     */
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
