package com.exceptions;

public class StudentExistsException extends Exception{
    public StudentExistsException() {
    }

    public StudentExistsException(String message) {
        super(message);
    }
}
