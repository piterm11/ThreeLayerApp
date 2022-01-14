package com.exceptions;

public class GradeOutOfRangeException extends Exception{
    public GradeOutOfRangeException() {
    }

    public GradeOutOfRangeException(String message) {
        super(message);
    }
}
