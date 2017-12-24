package com.projectStage1.exceptions;

public class CustomerNameExistException extends Exception 
{
    public CustomerNameExistException() {
        super();    
    }

    public CustomerNameExistException(String message) {
        super(message);
    }

}
