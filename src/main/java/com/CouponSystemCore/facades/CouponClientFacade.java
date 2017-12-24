package com.CouponSystemCore.facades;

import com.CouponSystemCore.exceptions.CompanyNotFoundException;
import com.CouponSystemCore.exceptions.CustomerNotFoundException;
import com.CouponSystemCore.exceptions.GetCustomerByNameException;
import com.CouponSystemCore.exceptions.LoginException;

public interface CouponClientFacade 
{
    public CouponClientFacade login(String name, String password) throws LoginException, 
                    CompanyNotFoundException, GetCustomerByNameException, CustomerNotFoundException;

}
