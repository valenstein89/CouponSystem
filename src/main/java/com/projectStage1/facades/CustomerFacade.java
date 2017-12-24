package com.projectStage1.facades;

import java.util.Iterator;
import java.util.List;

import com.projectStage1.beans.Coupon;
import com.projectStage1.beans.CouponType;
import com.projectStage1.beans.Customer;
import com.projectStage1.dao.CustomerDAO;
import com.projectStage1.exceptions.CouponPurchaseException;
import com.projectStage1.exceptions.CustomerCreationException;
import com.projectStage1.exceptions.CustomerNotFoundException;
import com.projectStage1.exceptions.GetCouponByCustomerException;
import com.projectStage1.exceptions.GetCouponOfCustomerByPriceException;
import com.projectStage1.exceptions.GetCouponOfCustomerByTypeException;
import com.projectStage1.exceptions.GetCouponsOfCustomerException;
import com.projectStage1.exceptions.GetCustomerByNameException;
import com.projectStage1.exceptions.LoginException;
import com.projectStage1.exceptions.PurchaseCouponException;
import com.projectStage1.exceptions.RollbackDataBaseException;

public class CustomerFacade implements CouponClientFacade {

    private CustomerDAO custDAO;
    private Customer thisCust;

    public CustomerFacade() {

    }

    public CustomerFacade(CustomerDAO custDAO) 
    {
        setCustDAO(custDAO);
    }

//    public void purchaseCoupon(Coupon coup) throws CustomerCreationException, PurchaseCouponException, RollbackDataBaseException, GetCouponsOfCustomerException, GetCouponByCustomerException, CouponPurchaseException
//    {
//        // Check if already purchased
//        long ID = coup.getID();
//        long fetchedID = 0;
//        //Get all purchased coupons
//        List<Coupon> couponList = getAllPurchasedCoupons();
//        
//        Iterator<Coupon> iter = couponList.iterator();
//        while (iter.hasNext()) 
//        {
//            fetchedID = iter.next().getID();
//            // If this coupon exists in the list, then throw error
//            if (fetchedID == ID) {
//                throw new CouponPurchaseException("Coupon with ID: " + ID + " have been already purchased!");
//            }
//        }
//        // If coupon was not found in the list, purchase it.
//        custDAO.purchaseCoupon(thisCust, coup);
//        
//        System.out.println("Coupon " + coup.getTitle() + " purchased successfully.");
//    
//    }
    
    public void purchaseCoupon(long id) throws CustomerCreationException, PurchaseCouponException, RollbackDataBaseException, GetCouponsOfCustomerException, GetCouponByCustomerException, CouponPurchaseException
    {
        // Check if already purchased
        long fetchedID = 0;
        //Get all purchased coupons
        List<Coupon> couponList = getAllPurchasedCoupons();
        
        Iterator<Coupon> iter = couponList.iterator();
        while (iter.hasNext()) 
        {
            fetchedID = iter.next().getID();
            // If this coupon exists in the list, then throw error
            if (fetchedID == id) {
                throw new CouponPurchaseException("Coupon with ID: " + id + " have been already purchased!");
            }
        }
        // If coupon was not found in the list, purchase it.
        custDAO.purchaseCoupon(thisCust, id);
        
        System.out.println("Coupon purchased successfully.");
    
    }

    public List<Coupon> getAllPurchasedCoupons() throws GetCouponsOfCustomerException, GetCouponByCustomerException{
        return custDAO.getCoupons(thisCust);
    }

    public List<Coupon> getAllPurchasedCouponsByType(CouponType type) throws GetCouponOfCustomerByTypeException{
        return custDAO.getCouponsByType(thisCust, type);
    }

    public List<Coupon> getAllPurchasedCouponsByPrice(double price) throws GetCouponOfCustomerByPriceException{
        return custDAO.getCouponsByPrice(thisCust, price);
    }

    public void setCustDAO(CustomerDAO custDAO) {
        this.custDAO = custDAO;
    }

    public void setThisCust(Customer thisCust) {
        this.thisCust = thisCust;
    }

    @Override
    public CouponClientFacade login(String name, String password) throws LoginException, GetCustomerByNameException, CustomerNotFoundException {
        // Login check
        if (custDAO.login(name, password)){

            // Get customer object and initialize
            // for use with the facade methods
            setThisCust(custDAO.getCustomerByName(name));
        }

        return this;
    }

}
