package com.vinovibes.vinoapi.exceptions;

/**
 * Exception for wine not found.
 */
public class WineNotFoundException extends RuntimeException {

    public WineNotFoundException(String message) {
        super(message);
    }
}
