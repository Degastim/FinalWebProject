package com.epam.pharmacy.exception;

/**
 * An exception that provides information on errors occurred while processing a command.
 *
 * @author Yauheni Tsitou
 */
public class CommandException extends Exception {
    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
