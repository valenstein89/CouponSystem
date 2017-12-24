package com.CouponSystemCore.dao.dbDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.CouponSystemCore.beans.Coupon;
import com.CouponSystemCore.beans.CouponType;
import com.CouponSystemCore.beans.Customer;
import com.CouponSystemCore.connectionPool.ConnectionPool;
import com.CouponSystemCore.dao.CustomerDAO;
import com.CouponSystemCore.exceptions.CustomerCreationException;
import com.CouponSystemCore.exceptions.CustomerNameExistException;
import com.CouponSystemCore.exceptions.CustomerNotFoundException;
import com.CouponSystemCore.exceptions.CustomerRemovalException;
import com.CouponSystemCore.exceptions.CustomerUpdateException;
import com.CouponSystemCore.exceptions.GetAllCustomersException;
import com.CouponSystemCore.exceptions.GetCouponOfCustomerByPriceException;
import com.CouponSystemCore.exceptions.GetCouponOfCustomerByTypeException;
import com.CouponSystemCore.exceptions.GetCouponsOfCustomerException;
import com.CouponSystemCore.exceptions.GetCustomerByNameException;
import com.CouponSystemCore.exceptions.GetCustomerException;
import com.CouponSystemCore.exceptions.LoginException;
import com.CouponSystemCore.exceptions.PurchaseCouponException;
import com.CouponSystemCore.exceptions.RollbackDataBaseException;

public class CustomerDBDAO implements CustomerDAO
{
    private static ConnectionPool pool = null;

    @Override
    public void addCustomer(Customer customer) throws CustomerCreationException, RollbackDataBaseException
    {
        String name = customer.getCustName();

        Connection con = null;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new CustomerCreationException("System was unable to establish"
                    + " a connection to create customer: " + customer.getCustName());
        }

//        //SQL query for Coupon table
//        String query = "INSERT INTO Customer (ID, CUST_NAME, PASSWORD) VALUES (?,?,?)";
        //SQL query for Coupon table
        String query = "INSERT INTO Customer (CUST_NAME, PASSWORD) VALUES (?,?)";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            //Set statement's parameters
//            ps.setLong(1, customer.getID());
//            ps.setString(2, name);
//            ps.setString(3, customer.getPassword());
            ps.setString(1, name);
            ps.setString(2, customer.getPassword());

            //Execute statement
            ps.executeUpdate();

        } catch (SQLException e) {
//            try {
//                if (con != null){
//                    con.rollback();
//                }
//            } catch (SQLException e1) {
//            	e1.printStackTrace();
//                throw new RollbackDataBaseException("System error. Please contact customer service.");
//            }
            throw new CustomerCreationException("Failed to add customer " + customer.getCustName());
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public Customer getCustomer(long ID) throws CustomerNotFoundException, GetCustomerException
    {
        Customer customer = null;

        Connection con;
        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e1) {
            throw new GetCustomerException("System was unable to establish"
                    + " a connection.");
        }

        String query = "SELECT * FROM Customer WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, ID);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                customer = new Customer();
                customer.setID(ID);
                
                customer.setCustName(rs.getString("CUST_NAME"));
                customer.setPassword(rs.getString("PASSWORD"));
            } else {
                throw new CustomerNotFoundException();
            }

        } catch (CustomerNotFoundException e)
        {
            throw new CustomerNotFoundException("Customer with ID: " + ID + " does not exist!");
        } catch (SQLException e) {
            throw new CustomerNotFoundException("Failed to get customer.");
        } finally {
            pool.returnConnection(con);
        }

        return customer;
    }

    @Override
    public Customer getCustomerByName(String name) throws GetCustomerByNameException, CustomerNotFoundException
    {
        Customer customer = null;

        Connection con;
        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e1) {
            throw new GetCustomerByNameException("System was unable to establish"
                    + " a connection.");
        }

        String query = "SELECT * FROM Customer WHERE CUST_NAME = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                customer = new Customer();
                customer.setCustName(name);
                
                customer.setID(rs.getLong("ID"));
                customer.setPassword(rs.getString("PASSWORD"));

            } else {
                throw new CustomerNotFoundException();
            }

        } catch (CustomerNotFoundException e)
        {
            throw new CustomerNotFoundException("Customer " + name + " does not exist!");
        } catch (SQLException e) {
            throw new CustomerNotFoundException("Failed to get customer.");
        } finally {
            pool.returnConnection(con);
        }

        return customer;
    }

    @Override
    public void removeCustomer(Customer customer) throws CustomerRemovalException, RollbackDataBaseException
    {
        long ID = customer.getID();

        //Get connection from pool
        Connection con;
        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new CustomerRemovalException("System was unable to establish"
                    + " a connection to remove customer: " + customer.getCustName());
        }

        String customerCouponTableQuery = "DELETE FROM Customer_Coupon WHERE Cust_ID = ?";
        String customerTableQuery = "DELETE FROM Customer WHERE ID = ?";

        try (PreparedStatement customerCouponTableStmt = con.prepareStatement(customerCouponTableQuery); 
                PreparedStatement customerTableStmt = con.prepareStatement(customerTableQuery))
        {
            con.setAutoCommit(false);

            customerCouponTableStmt.setLong(1, ID);
            customerCouponTableStmt.executeUpdate();

            customerTableStmt.setLong(1, ID);
            customerTableStmt.executeUpdate();

            con.commit();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error. Please contact customer service!");
            }
            throw new CustomerRemovalException("Failed to remove customer " + customer.getCustName());
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerUpdateException, RollbackDataBaseException
    {

        Connection con;
        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new CustomerUpdateException("System was unable to establish"
                    + " a connection.");
        }		

        String query = "UPDATE Customer SET PASSWORD = ? WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, customer.getPassword());
            ps.setLong(2, customer.getID());

            ps.executeUpdate();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error. Please contact customer service.");
            }
            throw new CustomerUpdateException("Failed to update customer!");
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws GetAllCustomersException{
        List<Customer> customerList = new ArrayList<>();

        Connection con;
        try
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new GetAllCustomersException("System was unable to establish"
                    + " a connection to obtain customers list!");
        }

        String query = "SELECT * FROM Customer";

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query))
        {

            while (rs.next())
            {
                Customer customer = new Customer();
                
                customer.setID(rs.getLong("ID"));
                customer.setCustName(rs.getString("CUST_NAME"));
                customer.setPassword(rs.getString("PASSWORD"));

                customerList.add(customer);
            }
        } catch (SQLException e) {
            throw new GetAllCustomersException("Failed to obtain customers list!");
        } finally {
            pool.returnConnection(con);
        }

        return customerList;
    }
    
    @Override
    public boolean customerNameExists(String name) throws GetAllCustomersException, CustomerNameExistException{
        // Create query String
        String query = "SELECT * FROM Customer WHERE CUST_NAME = ?";
        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new GetAllCustomersException("System was unable to establish"
                    + " a connection!");
        }

        // Get result from DB
        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new CustomerNameExistException("Error while checking customer name!");
        }

    }

//    @Override
//    public void purchaseCoupon(Customer cust, Coupon coup) throws CustomerCreationException, PurchaseCouponException, RollbackDataBaseException
//    {
//        long custID = cust.getID();
//        long coupID = coup.getID();
//
//        // Get connection from pool
//        Connection con = null;
//
//        try 
//        {
//            pool = ConnectionPool.getInstance();
//            con = pool.getConnection();
//
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new PurchaseCouponException("System was unable to establish a connection.");
//        }
//
//        // 1. Check if the coupon already purchased by the customer
//        String checkPurchased = "SELECT * FROM Customer_Coupon WHERE Cust_ID = ? and Coupon_ID = ?";
//
//        // 2. Check amount of coupons, and date of expiry
//        String checkAmntExp = "SELECT AMOUNT, END_DATE FROM Coupon WHERE ID = ?";
//
//        // 3. Update Customer_Coupon table (actual purchase)
//        String purchase = "INSERT INTO Customer_Coupon (Cust_ID, Coupon_ID) VALUES (?,?)";
//
//        // 4. Update amount field in Coupon table
//        String updateCoupTable = "UPDATE Coupon SET AMOUNT = AMOUNT - 1 WHERE ID = ?";
//
//        try (PreparedStatement checkPurchasedStmt = con.prepareStatement(checkPurchased);
//                PreparedStatement checkAmntExpStmt = con.prepareStatement(checkAmntExp);
//                PreparedStatement purchaseStmt = con.prepareStatement(purchase);
//                PreparedStatement updateCoupTableStmt = con.prepareStatement(updateCoupTable)) 
//        {
//            checkPurchasedStmt.setLong(1, custID);
//            checkPurchasedStmt.setLong(2, coupID);
//            ResultSet checkPurchasedRS = checkPurchasedStmt.executeQuery();
//
//            if (checkPurchasedRS.next()){
//                throw new PurchaseCouponException("This coupon was already purchased. "
//                        + "You can't purchase more of this coupon.");
//            } else {
//
//                checkAmntExpStmt.setLong(1, coupID);
//                ResultSet checkAmntExpRS = checkAmntExpStmt.executeQuery();
//
//                // Get dates (in milliseconds) to compare
//                if (checkAmntExpRS.next()){
//                    Long endDate = checkAmntExpRS.getDate("END_DATE").getTime();
//                    Long currTime = new java.util.Date().getTime();
//                    
//                    
//                    // Get amount of remaining coupons
//                    int amount = checkAmntExpRS.getInt("AMOUNT");
//    
//                    if (amount < 1 || endDate < currTime)
//                    {
//                        throw new PurchaseCouponException("No more coupons left in the company.");
//                    } else {
//    
//                        purchaseStmt.setLong(1, custID);
//                        purchaseStmt .setLong(2, coupID);
//                        purchaseStmt .executeUpdate();
//    
//                        updateCoupTableStmt.setLong(1, coupID);
//                        updateCoupTableStmt.executeUpdate();
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            try {
//                con.rollback();
//            } catch (SQLException e1) {
//                throw new RollbackDataBaseException("System error. Please contact customer service.");
//            }
//            throw new PurchaseCouponException("Failed to purchase coupon. Please contact customer service.");
//
//        } catch (PurchaseCouponException e) {
//
//            throw e;
//        } finally {
//            pool.returnConnection(con);
//        }
//
//    }
    
    @Override
    public void purchaseCoupon(Customer cust, long id) throws CustomerCreationException, PurchaseCouponException, RollbackDataBaseException
    {
        long custID = cust.getID();

        // Get connection from pool
        Connection con = null;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new PurchaseCouponException("System was unable to establish a connection.");
        }

        // 1. Check if the coupon already purchased by the customer
        String checkPurchased = "SELECT * FROM Customer_Coupon WHERE Cust_ID = ? and Coupon_ID = ?";

        // 2. Check amount of coupons, and date of expiry
        String checkAmntExp = "SELECT AMOUNT, END_DATE FROM Coupon WHERE ID = ?";

        // 3. Update Customer_Coupon table (actual purchase)
        String purchase = "INSERT INTO Customer_Coupon (Cust_ID, Coupon_ID) VALUES (?,?)";

        // 4. Update amount field in Coupon table
        String updateCoupTable = "UPDATE Coupon SET AMOUNT = AMOUNT - 1 WHERE ID = ?";

        try (PreparedStatement checkPurchasedStmt = con.prepareStatement(checkPurchased);
                PreparedStatement checkAmntExpStmt = con.prepareStatement(checkAmntExp);
                PreparedStatement purchaseStmt = con.prepareStatement(purchase);
                PreparedStatement updateCoupTableStmt = con.prepareStatement(updateCoupTable)) 
        {
            checkPurchasedStmt.setLong(1, custID);
            checkPurchasedStmt.setLong(2, id);
            ResultSet checkPurchasedRS = checkPurchasedStmt.executeQuery();

            if (checkPurchasedRS.next()){
                throw new PurchaseCouponException("This coupon was already purchased. "
                        + "You can't purchase more of this coupon.");
            } else {

                checkAmntExpStmt.setLong(1, id);
                ResultSet checkAmntExpRS = checkAmntExpStmt.executeQuery();

                // Get dates (in milliseconds) to compare
                if (checkAmntExpRS.next()){
                    Long endDate = checkAmntExpRS.getDate("END_DATE").getTime();
                    Long currTime = new java.util.Date().getTime();
                    
                    
                    // Get amount of remaining coupons
                    int amount = checkAmntExpRS.getInt("AMOUNT");
    
                    if (amount < 1 || endDate < currTime)
                    {
                        throw new PurchaseCouponException("No more coupons left in the company.");
                    } else {
    
                        purchaseStmt.setLong(1, custID);
                        purchaseStmt .setLong(2, id);
                        purchaseStmt .executeUpdate();
    
                        updateCoupTableStmt.setLong(1, id);
                        updateCoupTableStmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error. Please contact customer service.");
            }
            throw new PurchaseCouponException("Failed to purchase coupon. Please contact customer service.");

        } catch (PurchaseCouponException e) {

            throw e;
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public List<Coupon> getCoupons(Customer cust) throws GetCouponsOfCustomerException
    {        
        List<Coupon> couponList = new ArrayList<>();

        // Get connection from pool
        Connection con = null;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new GetCouponsOfCustomerException("System was unable to establish a connection.");
        }

        String query = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE"
                + " FROM Customer_Coupon CC JOIN Coupon C"
                + " ON CC.Coupon_ID = C.ID WHERE Cust_ID = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, cust.getID());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                long ID = rs.getLong("ID");
                String title = rs.getString("TITLE");
                Date startDate = rs.getDate("START_DATE");
                Date endDate = rs.getDate("END_DATE");
                int amount = rs.getInt("AMOUNT");
                CouponType type = CouponType.valueOf(rs.getString("TYPE").toUpperCase());
                String message = rs.getString("MESSAGE");
                double price = rs.getDouble("PRICE");
                String image = rs.getString("image");
                couponList.add(new Coupon(ID, title, startDate, endDate, amount, type, message, price, image));

            }
        } catch (SQLException e) {
            throw new GetCouponsOfCustomerException("Failed to ontain coupons list. PLease contact customer service.");
        } finally {
            pool.returnConnection(con);
        }

        return couponList;
    }

    @Override
    public List<Coupon> getCouponsByType(Customer cust, CouponType type) throws GetCouponOfCustomerByTypeException
    {
        List<Coupon> couponList = new ArrayList<>();

        // Get connection from pool
        Connection con = null;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new GetCouponOfCustomerByTypeException("System was unable to establish a connection.");
        }

        String query = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT, MESSAGE, PRICE, IMAGE"
                + " FROM Customer_Coupon CC JOIN Coupon C"
                + " ON CC.Coupon_ID = C.ID WHERE Cust_ID = ? AND TYPE = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, cust.getID());
            ps.setString(2, type.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                long ID = rs.getLong("ID");
                String title = rs.getString("TITLE");
                Date startDate = rs.getDate("START_DATE");
                Date endDate = rs.getDate("END_DATE");
                int amount = rs.getInt("AMOUNT");
                String message = rs.getString("MESSAGE");
                double price = rs.getDouble("PRICE");
                String image = rs.getString("image");
                couponList.add(new Coupon(ID, title, startDate, endDate, amount, type, message, price, image));

            }
        } catch (SQLException e) {
            throw new GetCouponOfCustomerByTypeException("Failed to ontain coupons list. PLease contact customer service.");
        } finally {
            pool.returnConnection(con);
        }

        return couponList;
    }

    @Override
    public List<Coupon> getCouponsByPrice(Customer cust, double price) throws GetCouponOfCustomerByPriceException
    {
        List<Coupon> couponList = new ArrayList<>();

        // Get connection from pool
        Connection con = null;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new GetCouponOfCustomerByPriceException("System was unable to establish a connection.");
        }

        String query = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, PRICE, MESSAGE, IMAGE"
                + " FROM Customer_Coupon CC JOIN Coupon C"
                + " ON CC.Coupon_ID = C.ID WHERE Cust_ID = ? AND PRICE <= ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, cust.getID());
            ps.setDouble(2, price);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                long ID = rs.getLong("ID");
                String title = rs.getString("TITLE");
                Date startDate = rs.getDate("START_DATE");
                Date endDate = rs.getDate("END_DATE");
                int amount = rs.getInt("AMOUNT");
                CouponType type = CouponType.valueOf(rs.getString("TYPE").toUpperCase());
                String message = rs.getString("MESSAGE");
                double fetchedPrice = rs.getDouble("PRICE");
                String image = rs.getString("image");
                couponList.add(new Coupon(ID, title, startDate, endDate, amount, type, message, fetchedPrice, image));

            }
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new GetCouponOfCustomerByPriceException("Failed to ontain coupons list. PLease contact customer service.");
        } finally {
            pool.returnConnection(con);
        }

        return couponList;

    }

    @Override
    public boolean login(String name, String password) throws LoginException 
    {
        String resultPassword = "";

        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e) {
            throw new LoginException("System was unable to establish"
                    + " a connection to login!");
        }

        String query = "SELECT PASSWORD FROM Customer WHERE CUST_NAME = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                resultPassword = rs.getString("PASSWORD");
            } else {
                throw new LoginException("No info about that customer in the DB.");
            }

            if (resultPassword.equals(password)){
                return true;
            } else {
                throw new LoginException("Incorrect password!");
            }
        } catch (SQLException e) {
            throw new LoginException("System error. Please try again in several minutes.");
        } catch (LoginException e) {
            throw new LoginException(e.getMessage());
        } finally {
            pool.returnConnection(con);
        }

    }

}


