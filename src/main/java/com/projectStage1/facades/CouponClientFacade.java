package com.projectStage1.facades;

import com.projectStage1.exceptions.CompanyNotFoundException;
import com.projectStage1.exceptions.CustomerNotFoundException;
import com.projectStage1.exceptions.GetCustomerByNameException;
import com.projectStage1.exceptions.LoginException;

public interface CouponClientFacade 
{
    public CouponClientFacade login(String name, String password) throws LoginException, 
                    CompanyNotFoundException, GetCustomerByNameException, CustomerNotFoundException;

}
