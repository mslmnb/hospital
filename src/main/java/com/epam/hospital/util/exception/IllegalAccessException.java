package com.epam.hospital.util.exception;

/**
 * This exception is thrown to indicate that user has no rights of access
 */
public class IllegalAccessException extends RuntimeException{
    public IllegalAccessException(String message) {
        super(message);
    }
}
