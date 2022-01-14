package com.exceptions;

public class SubjectExistsException extends Exception{
    public SubjectExistsException() {
    }

    public SubjectExistsException(String message) {
        super(message);
    }
}
