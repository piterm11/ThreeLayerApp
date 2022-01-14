package com.exceptions;

public class GradeExistsException extends Exception{
    public GradeExistsException() {
    }

    public GradeExistsException(String message) {
        super(message);
    }
}
