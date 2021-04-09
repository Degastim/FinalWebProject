package com.epam.pharmacy.exception;

/**
 * An exception that provides information on errors thrown by a ConnectionPool.
 *
 * @author Yauheni Tsitou
 */
public class ConnectionPoolException extends Exception {
    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
