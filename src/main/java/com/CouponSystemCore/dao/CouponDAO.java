package com.CouponSystemCore.dao;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import com.CouponSystemCore.beans.Company;
import com.CouponSystemCore.beans.Coupon;
import com.CouponSystemCore.beans.CouponType;
import com.CouponSystemCore.exceptions.CouponCreationException;
import com.CouponSystemCore.exceptions.CouponNameExistException;
import com.CouponSystemCore.exceptions.CouponNotAvailableException;
import com.CouponSystemCore.exceptions.CouponRemovalException;
import com.CouponSystemCore.exceptions.CouponUpdateException;
import com.CouponSystemCore.exceptions.GetAllCouponException;
import com.CouponSystemCore.exceptions.GetCouponsByCompanyException;
import com.CouponSystemCore.exceptions.GetCouponsByTypeException;
import com.CouponSystemCore.exceptions.GetCouponsOfCompanyByTypeException;
import com.CouponSystemCore.exceptions.GetCouponsOfCompanyMaxDateException;
import com.CouponSystemCore.exceptions.GetCouponsOfCompanyMaxPriceException;
import com.CouponSystemCore.exceptions.GetOldCouponsException;
import com.CouponSystemCore.exceptions.RemoveCouponsOfCompanyException;
import com.CouponSystemCore.exceptions.RollbackDataBaseException;

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