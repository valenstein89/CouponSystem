package com.CouponSystemCore.main;

import java.sql.SQLException;

import com.CouponSystemCore.connectionPool.ConnectionPool;
import com.CouponSystemCore.dao.CompanyDAO;
import com.CouponSystemCore.dao.CouponDAO;
import com.CouponSystemCore.dao.CustomerDAO;
import com.CouponSystemCore.dao.dbDao.CompanyDBDAO;
import com.CouponSystemCore.dao.dbDao.CouponDBDAO;
import com.CouponSystemCore.dao.dbDao.CustomerDBDAO;
import com.CouponSystemCore.exceptions.CompanyNotFoundException;
import com.CouponSystemCore.exceptions.CustomerNotFoundException;
import com.CouponSystemCore.exceptions.GetCustomerByNameException;
import com.CouponSystemCore.exceptions.LoginException;
import com.CouponSystemCore.facades.AdminFacade;
import com.CouponSystemCore.facades.ClientType;
import com.CouponSystemCore.facades.CompanyFacade;
import com.CouponSystemCore.facades.CouponClientFacade;
import com.CouponSystemCore.facades.CustomerFacade;
import com.CouponSystemCore.util.DailyCouponExpirationTask;

public class CouponSystem
{
    private static CouponSystem instance;
    private CompanyDAO compDAO;
    private CustomerDAO custDAO;
    private CouponDAO coupDAO ;
    private DailyCouponExpirationTask expTask;

    private CouponSystem()
    {
        setCompDAO(new CompanyDBDAO());
        setCustDAO(new CustomerDBDAO());
        setCoupDAO(new CouponDBDAO());
        
        expTask = new DailyCouponExpirationTask(coupDAO);
        expTask.startTask();
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

    public void setExpTask(DailyCouponExpirationTask expTask) {
        this.expTask = expTask;
    }

    public static CouponSystem getInstance()
    {
        if (instance == null)
        {
            instance = new CouponSystem();
        }
        
        return instance;
    }

    public CouponClientFacade login(String name, String password, ClientType type) throws LoginException, CompanyNotFoundException, GetCustomerByNameException, CustomerNotFoundException
    {
        switch (type)
        {
        // Creates AdminFacade
        case ADMIN:
            AdminFacade adminFacade = new AdminFacade(compDAO, custDAO, coupDAO);
            return adminFacade.login(name, password);
            // Creates CompanyFacade that contains it's specific company object for DAO operations
        case COMPANY:
            CompanyFacade companyFacade = new CompanyFacade(coupDAO, compDAO);
            return companyFacade.login(name, password);
            // Creates CustomerFacade that contains it's specific customer object for DAO operations
        case CUSTOMER:
            CustomerFacade customerFacade = new CustomerFacade(custDAO);
            return customerFacade.login(name, password);

        default :
            throw new LoginException("No such client type: " + type.name() + "!");
        }//switch
    }//login method

    public void shutdown() throws ClassNotFoundException, SQLException{
        ConnectionPool.getInstance().closeAllConnections();

        expTask.stopTask();
    }
}