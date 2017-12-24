package com.projectStage1.exceptions;

public class CustomerCreationException extends Exception 
{
    public CustomerCreationException() {
	super();
    }

    public CustomerCreationException(String message) {
	super(message);
    }
    
}