package com.CouponSystemCore.facades;

import java.util.List;

import com.CouponSystemCore.beans.Company;
import com.CouponSystemCore.beans.Customer;
import com.CouponSystemCore.dao.CompanyDAO;
import com.CouponSystemCore.dao.CouponDAO;
import com.CouponSystemCore.dao.CustomerDAO;
import com.CouponSystemCore.exceptions.CompanyCreationException;
import com.CouponSystemCore.exceptions.CompanyNameExistException;
import com.CouponSystemCore.exceptions.CompanyNotFoundException;
import com.CouponSystemCore.exceptions.CompanyRemovalException;
import com.CouponSystemCore.exceptions.CompanyUpdateException;
import com.CouponSystemCore.exceptions.CustomerCreationException;
import com.CouponSystemCore.exceptions.CustomerNameExistException;
import com.CouponSystemCore.exceptions.CustomerNotFoundException;
import com.CouponSystemCore.exceptions.CustomerRemovalException;
import com.CouponSystemCore.exceptions.CustomerUpdateException;
import com.CouponSystemCore.exceptions.GetAllCompaniesException;
import com.CouponSystemCore.exceptions.GetAllCouponException;
import com.CouponSystemCore.exceptions.GetAllCustomersException;
import com.CouponSystemCore.exceptions.GetCustomerException;
import com.CouponSystemCore.exceptions.LoginException;
import com.CouponSystemCore.exceptions.RemoveCouponsOfCompanyException;
import com.CouponSystemCore.exceptions.RollbackDataBaseException;

public class AdminFacade implements CouponClientFacade
{
    private CompanyDAO compDAO;
    private CustomerDAO custDAO;
    private CouponDAO coupDAO;

    public AdminFacade() {
    }
    
    public AdminFacade(CompanyDAO compDAO, CustomerDAO custDAO, CouponDAO coupDAO)
    {
        setCompDAO(compDAO);
        setCustDAO(custDAO);
        setCoupDAO(coupDAO);
    }
    
    public void createCompany(Company company) throws CompanyCreationException, GetAllCompaniesException, GetAllCouponException, CompanyNameExistException, RollbackDataBaseException
    {
        String name = company.getCompName();

        // Check title duplicate
        if (compDAO.companyNameExists(name)){
            throw new CompanyCreationException("Company " + name + " already exists!");
        } else {
        
        // Create Company
        compDAO.createCompany(company);
        }
        
        System.out.println("Company " + name + " was created successfully.");
    }
    
    public void removeCompany(Company company) throws CompanyRemovalException, RemoveCouponsOfCompanyException, RollbackDataBaseException, CompanyNotFoundException
    {
        long ID = company.getID();
        
        Company resComp = compDAO.getCompany(ID);
        if (resComp != null) 
        {
            // Remove the company
            compDAO.removeCompany(resComp);    
            // Remove company's coupons
            coupDAO.removeCouponsOfCompany(resComp);        
        } else {
            throw new CompanyRemovalException("Company with ID: " + ID + " does not exist!");
        }
        
        System.out.println("Company " + resComp.getCompName() + " was successfully removed from the system.");
    }
    
    public void updateCompany(Company company) throws CompanyUpdateException, CompanyNotFoundException, RollbackDataBaseException
    {
        // Get company ID
        long ID = company.getID();
        
        // Check if company exists:
        // Get company by ID
        Company resComp = compDAO.getCompany(ID);
        // Actual check
        if (resComp != null){
            // Check that company name is not to be changed
            if (resComp.getCompName().equals(company.getCompName())){
                compDAO.updateCompany(company);
            } else {
                throw new CompanyUpdateException("Cannot change company name!");
            }
        } else {
            throw new CompanyNotFoundException("Company with this ID does not exist!");
        }
        
        System.out.println("Company " + resComp.getCompName() + " was successfully updated.");
    }
    
    public Company getCompany(long ID) throws CompanyNotFoundException
    {
        Company company = compDAO.getCompany(ID);
        // Check if company exists
        if (company != null){
            return company;
        } else {
            throw new CompanyNotFoundException("Company does not exist!");
        }
    }
    
    public List<Company> getAllCompanies() throws GetAllCompaniesException
    {
        List <Company> compList = compDAO.getAllCompanies();
        
        // Check that the list is not empty
        if (compList.size() == 0){
            throw new GetAllCompaniesException("There are no companies in the system.");
        } else {
            return compList;
        }
    }
    
    public void createCustomer(Customer customer) throws CustomerNameExistException, CustomerCreationException, GetAllCustomersException, RollbackDataBaseException
    {
        String name = customer.getCustName();

        // Check name duplicate
        if (custDAO.customerNameExists(name)){
            throw new CustomerCreationException("Customer " + name + " already exists!");
        } else {
            // Create Customer
            custDAO.addCustomer(customer);
        }
        
        System.out.println("Customer " + customer.getCustName() + " was created successfully.");
        
    }
    
    public void removeCustomer(Customer customer) throws CustomerRemovalException, RollbackDataBaseException, GetCustomerException, CustomerNotFoundException
    {
        long ID = customer.getID();
        // Check if customer exists
        Customer resCust = custDAO.getCustomer(ID); 
        if (resCust != null) {
            // Remove the customer and his coupons from the system
            custDAO.removeCustomer(resCust);     
        } else {
            throw new CustomerRemovalException("Customer with ID: " + ID + " does not exist!");
        }
        System.out.println("Customer " + resCust.getCustName() + " was successfully removed.");
    }
    
    public void updateCustomer(Customer customer) throws CustomerNameExistException, GetAllCustomersException, CustomerUpdateException, RollbackDataBaseException, GetCustomerException, CustomerNotFoundException
    {
        long ID = customer.getID();
        
        Customer resCust = custDAO.getCustomer(ID);
        
        if (resCust != null) {
            custDAO.updateCustomer(customer);
        } else {
            throw new CustomerUpdateException("Customer with ID: " + ID + " does not exist!");
        }
        System.out.println("Customer " + resCust.getCustName() + " was successfully updated.");
    }
    
    public Customer getCustomer(long ID) throws GetCustomerException, CustomerNotFoundException
    {
        Customer customer;

        customer = custDAO.getCustomer(ID);
        
        return customer;
    }
    
    public List<Customer> getAllCustomers() throws GetAllCustomersException
    {
        List<Customer> customersList;
        
        customersList = custDAO.getAllCustomers();
        
        return customersList;
    }
 
    public void setCompDAO(CompanyDAO compDAO) {
        this.compDAO = compDAO;
    }

    public void setCustDAO(CustomerDAO custDAO) {
        this.custDAO = custDAO;
    }

    public void setCoupDAO(CouponDAO coupDAO) {
        this.coupDAO = coupDAO;
    }

    @Override
    public CouponClientFacade login(String username, String password) throws LoginException 
    {
        // Checking username and password    
        if (username.equals("admin") && password.equals("1234"))
        {
            return this;
            
        } else {
            throw new LoginException("Username and password you entered do not match."
                    + " Please try again.");
        }
    }

}