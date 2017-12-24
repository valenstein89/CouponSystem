package com.CouponSystemCore.exceptions;

public class CustomerNameExistException extends Exception 
{
    public CustomerNameExistException() {
        super();    
    }

    public CustomerNameExistException(String message) {
        super(message);
    }

}
