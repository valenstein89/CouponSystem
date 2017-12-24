package com.projectStage1.exceptions;

public class CustomerNotFoundException extends Exception 
{
    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

}
