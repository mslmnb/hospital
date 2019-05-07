package com.epam.hospital.util.exception;

/**
 * This exception is thrown to indicate that action is indefinite for some URI
 */
public class IndefiniteActionException extends RuntimeException{
    public IndefiniteActionException(String message) {
        super(message);
    }
}
