package com.CouponSystemCore.dao;

import java.util.List;

import com.CouponSystemCore.beans.Coupon;
import com.CouponSystemCore.beans.CouponType;
import com.CouponSystemCore.beans.Customer;
import com.CouponSystemCore.exceptions.CustomerCreationException;
import com.CouponSystemCore.exceptions.CustomerNameExistException;
import com.CouponSystemCore.exceptions.CustomerNotFoundException;
import com.CouponSystemCore.exceptions.CustomerRemovalException;
import com.CouponSystemCore.exceptions.CustomerUpdateException;
import com.CouponSystemCore.exceptions.GetAllCustomersException;
import com.CouponSystemCore.exceptions.GetCouponByCustomerException;
import com.CouponSystemCore.exceptions.GetCouponOfCustomerByPriceException;
import com.CouponSystemCore.exceptions.GetCouponOfCustomerByTypeException;
import com.CouponSystemCore.exceptions.GetCouponsOfCustomerException;
import com.CouponSystemCore.exceptions.GetCustomerByNameException;
import com.CouponSystemCore.exceptions.GetCustomerException;
import com.CouponSystemCore.exceptions.LoginException;
import com.CouponSystemCore.exceptions.PurchaseCouponException;
import com.CouponSystemCore.exceptions.RollbackDataBaseException;

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
