package com.exceptions;

public class StudentNotFoundException extends Exception{
    public StudentNotFoundException() {
    }

    public StudentNotFoundException(String message) {
        super(message);
    }
}
