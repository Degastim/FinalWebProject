package com.epam.pharmacy.command;

import com.epam.pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface that represents "Command" pattern. Used by a controller.
 *
 * @author Yauheni Tsitou
 */
public interface ActionCommand {
    /**
     * Processes a request from controller and returns the page to be redirected.
     *
     * @param request request object from page.
     * @return null if requested page doesn't exist.
     * @throws CommandException if an exception has occurred while executing.
     */
    CommandResult execute(HttpServletRequest request) throws CommandException;
}
