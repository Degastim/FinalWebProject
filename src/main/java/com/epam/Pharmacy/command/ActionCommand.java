package com.epam.Pharmacy.command;

import com.epam.Pharmacy.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionCommand {
    CommandResult execute(HttpServletRequest request) throws CommandException;
}
