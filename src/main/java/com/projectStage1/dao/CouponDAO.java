package com.projectStage1.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import com.projectStage1.beans.Company;
import com.projectStage1.beans.Coupon;
import com.projectStage1.beans.CouponType;
import com.projectStage1.exceptions.CouponCreationException;
import com.projectStage1.exceptions.CouponNameExistException;
import com.projectStage1.exceptions.CouponNotAvailableException;
import com.projectStage1.exceptions.CouponRemovalException;
import com.projectStage1.exceptions.CouponUpdateException;
import com.projectStage1.exceptions.GetAllCouponException;
import com.projectStage1.exceptions.GetCouponsByCompanyException;
import com.projectStage1.exceptions.GetCouponsByTypeException;
import com.projectStage1.exceptions.GetCouponsOfCompanyByTypeException;
import com.projectStage1.exceptions.GetCouponsOfCompanyMaxDateException;
import com.projectStage1.exceptions.GetCouponsOfCompanyMaxPriceException;
import com.projectStage1.exceptions.GetOldCouponsException;
import com.projectStage1.exceptions.RemoveCouponsOfCompanyException;
import com.projectStage1.exceptions.RollbackDataBaseException;

public interface CouponDAO
{
	public void createCoupon(Coupon coupon, long companyID) throws CouponCreationException, RollbackDataBaseException;
	public void removeCoupon(Coupon coupon) throws CouponRemovalException, RollbackDataBaseException;
	public void updateCoupon(Coupon coupon) throws CouponUpdateException, RollbackDataBaseException;
	public Coupon getCoupon(long ID) throws CouponNotAvailableException;
	public List<Coupon> getAllCoupon() throws GetAllCouponException;
	public List<Coupon> getCouponByType(CouponType type) throws GetCouponsByTypeException;
	public List<Coupon> getCouponByCompany(Company company) throws GetCouponsByCompanyException;
	public List<Coupon> getCouponsOfCompanyByType(Company comp, CouponType type) throws GetCouponsOfCompanyByTypeException;
	public List<Coupon> getCouponsOfCompanyMaxPrice(Company comp, double price) throws GetCouponsOfCompanyMaxPriceException;
        public void removeCouponsOfCompany(Company company) throws RemoveCouponsOfCompanyException, RollbackDataBaseException;
	public Collection<Coupon> getOldCoupons() throws CouponNotAvailableException, GetOldCouponsException;
	public boolean couponNameExists(String name) throws GetAllCouponException, CouponNameExistException;
        public List<Coupon> getCouponsOfCompanyMaxDate(Company comp, Date date) throws GetCouponsOfCompanyMaxDateException;
}