package com.epam.pharmacy.command;

import com.epam.pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
    CommandResult execute(HttpServletRequest request) throws CommandException;
}
