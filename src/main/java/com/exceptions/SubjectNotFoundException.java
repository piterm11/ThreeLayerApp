package com.exceptions;

public class SubjectNotFoundException extends Exception{
    public SubjectNotFoundException() {
    }

    public SubjectNotFoundException(String message) {
        super(message);
    }
}
