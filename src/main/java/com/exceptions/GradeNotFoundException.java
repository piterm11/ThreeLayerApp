package com.exceptions;

public class GradeNotFoundException extends Exception{
    public GradeNotFoundException() {
    }

    public GradeNotFoundException(String message) {
        super(message);
    }
}
