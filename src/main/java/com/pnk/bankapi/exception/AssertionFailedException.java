package com.pnk.bankapi.exception;

public class AssertionFailedException extends RuntimeException {
    public AssertionFailedException(String message) {
        super(message);
    }
}
