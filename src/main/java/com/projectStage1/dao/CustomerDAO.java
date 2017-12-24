package com.projectStage1.dao;

import java.util.List;

import com.projectStage1.beans.Coupon;
import com.projectStage1.beans.CouponType;
import com.projectStage1.beans.Customer;
import com.projectStage1.exceptions.CustomerCreationException;
import com.projectStage1.exceptions.CustomerNameExistException;
import com.projectStage1.exceptions.CustomerNotFoundException;
import com.projectStage1.exceptions.CustomerRemovalException;
import com.projectStage1.exceptions.CustomerUpdateException;
import com.projectStage1.exceptions.GetAllCustomersException;
import com.projectStage1.exceptions.GetCouponByCustomerException;
import com.projectStage1.exceptions.GetCouponOfCustomerByPriceException;
import com.projectStage1.exceptions.GetCouponOfCustomerByTypeException;
import com.projectStage1.exceptions.GetCouponsOfCustomerException;
import com.projectStage1.exceptions.GetCustomerByNameException;
import com.projectStage1.exceptions.GetCustomerException;
import com.projectStage1.exceptions.LoginException;
import com.projectStage1.exceptions.PurchaseCouponException;
import com.projectStage1.exceptions.RollbackDataBaseException;

public interface CustomerDAO
{
    public void addCustomer(Customer customer) throws CustomerCreationException, RollbackDataBaseException;
    public Customer getCustomer(long id) throws GetCustomerException, CustomerNotFoundException;
    public void removeCustomer(Customer customer) throws CustomerRemovalException, RollbackDataBaseException;
    public void updateCustomer(Customer customer) throws CustomerUpdateException, RollbackDataBaseException;
    public List<Customer> getAllCustomers() throws GetAllCustomersException;
    public boolean login(String name, String password) throws LoginException;
    public boolean customerNameExists(String name) throws GetAllCustomersException, CustomerNameExistException;
    public void purchaseCoupon(Customer cust, long id) throws CustomerCreationException, PurchaseCouponException, RollbackDataBaseException;
    public List<Coupon> getCoupons(Customer cust) throws GetCouponsOfCustomerException, GetCouponByCustomerException;
    public List<Coupon> getCouponsByType(Customer cust, CouponType type) throws GetCouponOfCustomerByTypeException;
    public List<Coupon> getCouponsByPrice(Customer cust, double price) throws GetCouponOfCustomerByPriceException;
    public Customer getCustomerByName(String name) throws GetCustomerByNameException, CustomerNotFoundException;

}
