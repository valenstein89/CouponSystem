package com.projectStage1.exceptions;

public class CouponNameExistException extends Exception {

    public CouponNameExistException() {
        super();
    }

    public CouponNameExistException(String message) {
        super(message);
    }

}
