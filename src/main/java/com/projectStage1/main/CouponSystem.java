package com.projectStage1.main;

import java.sql.SQLException;

import com.projectStage1.connectionPool.ConnectionPool;
import com.projectStage1.dao.CompanyDAO;
import com.projectStage1.dao.CouponDAO;
import com.projectStage1.dao.CustomerDAO;
import com.projectStage1.dao.dbDao.CompanyDBDAO;
import com.projectStage1.dao.dbDao.CouponDBDAO;
import com.projectStage1.dao.dbDao.CustomerDBDAO;
import com.projectStage1.exceptions.CompanyNotFoundException;
import com.projectStage1.exceptions.CustomerNotFoundException;
import com.projectStage1.exceptions.GetCustomerByNameException;
import com.projectStage1.exceptions.LoginException;
import com.projectStage1.facades.AdminFacade;
import com.projectStage1.facades.ClientType;
import com.projectStage1.facades.CompanyFacade;
import com.projectStage1.facades.CouponClientFacade;
import com.projectStage1.facades.CustomerFacade;
import com.projectStage1.util.DailyCouponExpirationTask;

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