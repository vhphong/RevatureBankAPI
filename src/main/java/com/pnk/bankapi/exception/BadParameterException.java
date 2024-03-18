package com.pnk.bankapi.exception;

public class BadParameterException extends Exception {

    public BadParameterException() {
        super();
    }

    public BadParameterException(String message) {
        super(message);
    }

    public BadParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadParameterException(Throwable cause) {
        super(cause);
    }

    public BadParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}