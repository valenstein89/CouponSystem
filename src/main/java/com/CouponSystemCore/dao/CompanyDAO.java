package com.CouponSystemCore.dao;

import java.util.List;

import com.CouponSystemCore.beans.Company;
import com.CouponSystemCore.exceptions.CompanyCreationException;
import com.CouponSystemCore.exceptions.CompanyNameExistException;
import com.CouponSystemCore.exceptions.CompanyNotFoundException;
import com.CouponSystemCore.exceptions.CompanyRemovalException;
import com.CouponSystemCore.exceptions.CompanyUpdateException;
import com.CouponSystemCore.exceptions.GetAllCompaniesException;
import com.CouponSystemCore.exceptions.GetAllCouponException;
import com.CouponSystemCore.exceptions.LoginException;
import com.CouponSystemCore.exceptions.RollbackDataBaseException;;

public interface CompanyDAO
{
    public void createCompany(Company company) throws CompanyCreationException, RollbackDataBaseException;
    public void removeCompany(Company company) throws CompanyRemovalException, RollbackDataBaseException;
    public void updateCompany (Company company) throws CompanyUpdateException, RollbackDataBaseException;
    public Company getCompany(long ID) throws CompanyNotFoundException;
    public List<Company> getAllCompanies() throws GetAllCompaniesException;
    public boolean login(String companyName, String password) throws LoginException;
    boolean companyNameExists(String name) throws GetAllCouponException, CompanyNameExistException;
    Company getCompanyByName(String companyName) throws CompanyNotFoundException;
}