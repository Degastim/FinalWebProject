package com.epam.pharmacy.exception;

/**
 * An exception that provides information on errors thrown by Dao objects.
 *
 * @author Yauheni Tsitou
 */
public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
