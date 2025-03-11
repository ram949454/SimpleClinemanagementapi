package com.client.simplerestapi.exception;

// exception for duplicate MobileNumber
public class DuplicateMobileNumberException extends RuntimeException {

    public DuplicateMobileNumberException(String message) {
        super(message);
    }
}