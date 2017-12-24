package com.projectStage1.exceptions;

public class CouponNotAvailableException extends Exception 
{
    public CouponNotAvailableException() {
        super();
    }

    public CouponNotAvailableException(String message)
    {
       super(message);
    }
}
