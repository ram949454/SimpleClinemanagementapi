package com.client.simplerestapi.exception;

//  exception for duplicate entry Id number
public class DuplicateIdNumberException extends RuntimeException {

    public DuplicateIdNumberException(String message) {
        super(message);
    }
}