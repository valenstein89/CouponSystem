package com.projectStage1.dao;

import java.util.List;

import com.projectStage1.beans.Company;
import com.projectStage1.exceptions.CompanyCreationException;
import com.projectStage1.exceptions.CompanyNameExistException;
import com.projectStage1.exceptions.CompanyNotFoundException;
import com.projectStage1.exceptions.CompanyRemovalException;
import com.projectStage1.exceptions.CompanyUpdateException;
import com.projectStage1.exceptions.GetAllCompaniesException;
import com.projectStage1.exceptions.GetAllCouponException;
import com.projectStage1.exceptions.LoginException;
import com.projectStage1.exceptions.RollbackDataBaseException;;

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