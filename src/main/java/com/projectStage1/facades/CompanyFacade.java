package com.projectStage1.facades;

import java.sql.Date;
import java.util.List;

import com.projectStage1.beans.Company;
import com.projectStage1.beans.Coupon;
import com.projectStage1.beans.CouponType;
import com.projectStage1.dao.CompanyDAO;
import com.projectStage1.dao.CouponDAO;
import com.projectStage1.exceptions.CompanyNotFoundException;
import com.projectStage1.exceptions.CouponCreationException;
import com.projectStage1.exceptions.CouponNameExistException;
import com.projectStage1.exceptions.CouponNotAvailableException;
import com.projectStage1.exceptions.CouponRemovalException;
import com.projectStage1.exceptions.CouponUpdateException;
import com.projectStage1.exceptions.GetAllCouponException;
import com.projectStage1.exceptions.GetCouponsByCompanyException;
import com.projectStage1.exceptions.GetCouponsOfCompanyByTypeException;
import com.projectStage1.exceptions.GetCouponsOfCompanyMaxDateException;
import com.projectStage1.exceptions.GetCouponsOfCompanyMaxPriceException;
import com.projectStage1.exceptions.LoginException;
import com.projectStage1.exceptions.RollbackDataBaseException;

public class CompanyFacade implements CouponClientFacade {

    private CouponDAO coupDAO;
    private CompanyDAO compDAO;
    // "this" Company object for use in methods
    private Company company;

    public CompanyFacade() {

    }

    public CompanyFacade(CouponDAO coupDAO, CompanyDAO compDAO) 
    {
        setCompDAO(compDAO);
        setCoupDAO(coupDAO);
    }

    public CouponDAO getCoupDAO() {
        return coupDAO;
    }

    public void setCoupDAO(CouponDAO coupDAO) {
        this.coupDAO = coupDAO;
    }

    public CompanyDAO getCompDAO() {
        return compDAO;
    }

    public void setCompDAO(CompanyDAO compDAO) {
        this.compDAO = compDAO;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void createCoupon(Coupon coupon) throws CouponCreationException, GetAllCouponException, CouponNameExistException, RollbackDataBaseException
    {
        String title = coupon.getTitle();

        if (coupDAO.couponNameExists(title)){
            throw new CouponNameExistException("Coupon " + title + " already exists!");
        } else {
            coupDAO.createCoupon(coupon, company.getID());
        }

        System.out.println("Coupon " + title + " was successfully created.");
    }

    public void removeCoupon(Coupon coupon) throws CouponRemovalException, RollbackDataBaseException, CouponNotAvailableException
    {
        long ID = coupon.getID();
        
        Coupon resCoup = coupDAO.getCoupon(ID); 
        if (resCoup != null) {
            coupDAO.removeCoupon(coupon);
        } else {
            throw new CouponNotAvailableException("Coupon with ID " + ID + " does not exist.");
        }
        System.out.println("Coupon " + resCoup.getTitle() + " was removed successfully.");
    }

    public void updateCoupon(Coupon coupon) throws CouponUpdateException, GetAllCouponException, CouponNameExistException, RollbackDataBaseException, CouponNotAvailableException
    {
        long ID = coupon.getID();
        
        Coupon resCoup = coupDAO.getCoupon(ID);
        String resTitle = "";
        // Check coupon exists
        if (resCoup != null) 
        {
            resTitle = resCoup.getTitle();
            // Check title not changed
            if (resTitle.equals(coupon.getTitle())) {
                coupDAO.updateCoupon(coupon);
            } else {
                throw new CouponUpdateException("Cannot change coupon's title!");
            }
        } else {
            throw new CouponNotAvailableException("Coupon with ID: " + ID + " does not exist.");
        }
        System.out.println("Coupon " + resTitle + " was updated successfully.");
    }

    public Coupon getCoupon(long ID) throws CouponNotAvailableException
    {
        return coupDAO.getCoupon(ID);

    }

    public List<Coupon> getAllCoupons() throws CompanyNotFoundException, GetCouponsByCompanyException
    {
        return coupDAO.getCouponByCompany(company);
    }

    public List<Coupon> getCouponsByType(CouponType type) throws GetCouponsOfCompanyByTypeException
    {    
        return coupDAO.getCouponsOfCompanyByType(company, type);
    }

    public List<Coupon> getCouponsMaxPrice(double price) throws GetCouponsOfCompanyMaxPriceException
    {
        return coupDAO.getCouponsOfCompanyMaxPrice(company, price);
    }

    public List<Coupon> getCouponsMaxDate(Date date) throws GetCouponsOfCompanyMaxDateException
    {
        return coupDAO.getCouponsOfCompanyMaxDate(company, date);
    }

    @Override
    public CouponClientFacade login(String name, String password) throws LoginException, CompanyNotFoundException 
    {
        // Login check
        if (compDAO.login(name, password)){

            // Get company object and initialize
            // for use with the facade methods
            setCompany(compDAO.getCompanyByName(name));
        }

        return this;
    }

}
