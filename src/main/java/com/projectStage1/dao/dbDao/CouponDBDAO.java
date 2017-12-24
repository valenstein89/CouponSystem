package com.projectStage1.dao.dbDao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.projectStage1.beans.Company;
import com.projectStage1.beans.Coupon;
import com.projectStage1.beans.CouponType;
import com.projectStage1.connectionPool.ConnectionPool;
import com.projectStage1.dao.CouponDAO;
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

public class CouponDBDAO implements CouponDAO
{	
    private static ConnectionPool pool;

    @Override
    public void createCoupon(Coupon coupon, long companyID) throws CouponCreationException, RollbackDataBaseException
    {
        long ID = 0;
        String title = coupon.getTitle();

        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e) {
            throw new CouponCreationException("System was unable to establish"
                    + " a connection.");
        }

        //SQL query for Coupon table
        String coupTabQuery = "INSERT INTO Coupon (TITLE,START_DATE,END_DATE,"
        		+ "AMOUNT,TYPE,MESSAGE,PRICE,IMAGE)"
        		+ " VALUES (?,?,?,?,?,?,?,?)";
        
        //SQL query to get coupon ID from the DB
        String coupIdQuery = "SELECT ID FROM Coupon WHERE TITLE = ?";
        
        //SQL query for Company_Coupon Table
        String compCoupTabQuery = "INSERT INTO Company_Coupon (Comp_ID, Coupon_ID)"
                + " VALUES (?,?)";

        try (PreparedStatement coupTabStmt = con.prepareStatement(coupTabQuery);
        		PreparedStatement coupIdStmt = con.prepareStatement(coupIdQuery);
                PreparedStatement compCoupTabStmt = con.prepareStatement(compCoupTabQuery))
        {

            con.setAutoCommit(false);

            // create a prepared statement
            coupTabStmt.setString(1, title);
            coupTabStmt.setDate(2, coupon.getStartDate());
            coupTabStmt.setDate(3, coupon.getEndDate());
            coupTabStmt.setInt(4, coupon.getAmount());
            coupTabStmt.setString(5, coupon.getType().name());
            coupTabStmt.setString(6, coupon.getMessage());
            coupTabStmt.setDouble(7, coupon.getPrice());
            coupTabStmt.setString(8, coupon.getImage());
            // execute statement
            coupTabStmt.executeUpdate();
            
            
            coupIdStmt.setString(1, title);
            ResultSet rs = coupIdStmt.executeQuery();
            if (rs.next()) {
            	ID = rs.getLong("ID");
            }
            
            //Create a prepared statement
            compCoupTabStmt.setLong(1, companyID);
            compCoupTabStmt.setLong(2, ID);
            //Execute statement
            compCoupTabStmt.executeUpdate();
            //Commit Statements for execution
            con.commit();
            
        } catch (SQLException e) {
            try {
                if (con != null){
                    con.rollback();
                }
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error. Please contact customer service.");
            }
            throw new CouponCreationException("Failed to create coupon: " + title);
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public void removeCoupon(Coupon coupon) throws CouponRemovalException, RollbackDataBaseException
    {
        long id = coupon.getID();

        //Get connection from pool
        Connection con;

        try
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new CouponRemovalException("System was unable to establish"
                    + " a connection.");
        }

        String couponTableQuery = "DELETE FROM Coupon WHERE ID = ?";
        String companyCouponTableQuery = "DELETE FROM Company_Coupon WHERE COUPON_ID = ?";
        String customerCouponTableQuery = "DELETE FROM Customer_Coupon WHERE COUPON_ID = ?";

        try (PreparedStatement couponTableStmt = con.prepareStatement(couponTableQuery); 
                PreparedStatement companyCouponTableStmt = con.prepareStatement(companyCouponTableQuery);
                PreparedStatement customerCouponTableStmt = con.prepareStatement(customerCouponTableQuery))
        {
            con.setAutoCommit(false);

            companyCouponTableStmt.setLong(1, id);
            companyCouponTableStmt.executeUpdate();

            customerCouponTableStmt.setLong(1, id);
            customerCouponTableStmt.executeUpdate();

            couponTableStmt.setLong(1, id);
            couponTableStmt.executeUpdate();

            con.commit();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error. Please contact customer service.");
            }
            throw new CouponRemovalException("Failed to remove coupon: " + coupon.getTitle());
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public void updateCoupon(Coupon coupon) throws CouponUpdateException, RollbackDataBaseException
    {
        Connection con;

        try
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new CouponUpdateException("System was unable to establish"
                    + " a connection.");
        }

        String query = "UPDATE Coupon SET START_DATE = ?, END_DATE = ?, "
                + "AMOUNT = ?, TYPE = ?, MESSAGE = ?, PRICE = ?, IMAGE = ? WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setDate(1, coupon.getStartDate());
            ps.setDate(2, coupon.getEndDate());
            ps.setInt(3, coupon.getAmount());
            ps.setString(4, coupon.getType().toString());
            ps.setString(5, coupon.getMessage());
            ps.setDouble(6, coupon.getPrice());
            ps.setString(7, coupon.getImage());
            ps.setLong(8, coupon.getID());

            ps.executeUpdate();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error. Please contact customer service.");
            }
            throw new CouponUpdateException("Failed to update coupon: " + coupon.getTitle());
        } finally {
            pool.returnConnection(con);
        }


    }

    @Override
    public Coupon getCoupon(long ID) throws CouponNotAvailableException
    {		
        Coupon coupon = null;

        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new CouponNotAvailableException("System was unable to establish"
                    + " a connection.");
        }

        String query = "SELECT * FROM Coupon WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, ID);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                coupon = new Coupon();
                coupon.setID(ID);

                coupon.setTitle(rs.getString("TITLE"));
                coupon.setStartDate(rs.getDate("START_DATE"));
                coupon.setEndDate(rs.getDate("END_DATE"));
                coupon.setAmount(rs.getInt("AMOUNT"));
                coupon.setType(CouponType.valueOf(rs.getString("TYPE").toUpperCase()));
                coupon.setMessage(rs.getString("MESSAGE"));
                coupon.setPrice(rs.getDouble("PRICE"));
                coupon.setImage(rs.getString("image"));


            } else {
                throw new CouponNotAvailableException();
            }

        } catch (CouponNotAvailableException e)
        {
            throw new CouponNotAvailableException("Coupon " + ID + " does not exist!");
        } catch (SQLException e) {
            throw new CouponNotAvailableException("Failed to get coupon.");
        } finally {
            pool.returnConnection(con);
        }

        return coupon;

    }

    @Override
    public List<Coupon> getAllCoupon() throws GetAllCouponException {

        List<Coupon> couponList = new ArrayList<>();

        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new GetAllCouponException("System was unable to establish"
                    + " a connection.");
        }

        String query = "SELECT * FROM Coupon";

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query))
        {

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
            throw new GetAllCouponException("Failed to obtain the coupons list.");
        } finally {
            pool.returnConnection(con);
        }

        return couponList;
    }

    @Override
    public List<Coupon> getCouponByType(CouponType type) throws GetCouponsByTypeException 
    {
        List<Coupon> couponList = new ArrayList<>();

        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e) {
            throw new GetCouponsByTypeException("System was unable to establish"
                    + " a connection.");
        }

        String query = "SELECT * FROM Coupon WHERE TYPE = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, type.name());
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()){
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
            throw new GetCouponsByTypeException("Failed to abtain the coupons list by type!");
        }

        return couponList;
    }

    @Override
    public List<Coupon> getCouponByCompany(Company company) throws GetCouponsByCompanyException 
    {
        List<Coupon> couponList = new ArrayList<>();

        // Get company name for use within the method
        String compName = company.getCompName();

        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e1) {
            throw new GetCouponsByCompanyException("System was unable to establish"
                    + " a connection.");
        }

        String query = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT,TYPE, MESSAGE, PRICE, IMAGE "
                + "FROM Company_Coupon CP JOIN Coupon C "
                + "ON CP.Coupon_ID = C.ID "
                + "WHERE Comp_ID = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, company.getID());
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Coupon coupon = new Coupon();
                
                coupon.setID(rs.getLong("ID"));
                coupon.setTitle(rs.getString("TITLE"));
                coupon.setStartDate(rs.getDate("START_DATE"));
                coupon.setEndDate(rs.getDate("END_DATE"));
                coupon.setAmount(rs.getInt("AMOUNT"));
                coupon.setType(CouponType.valueOf(rs.getString("TYPE").toUpperCase()));
                coupon.setMessage(rs.getString("MESSAGE"));
                coupon.setPrice(rs.getDouble("PRICE"));
                coupon.setImage(rs.getString("image"));

                couponList.add(coupon);           
            }

        } catch (SQLException e) {
            throw new GetCouponsByCompanyException("Failed to abtain the coupons list of " 
                    + compName + " company!");
        }

        return couponList;
    }

    @Override
    public List<Coupon> getCouponsOfCompanyByType(Company comp, CouponType type) throws GetCouponsOfCompanyByTypeException
    {
        List<Coupon> couponList = new ArrayList<>();

        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e1) {
            throw new GetCouponsOfCompanyByTypeException("System was unable to establish"
                    + " a connection.");
        }

        // Get coupons from JOIN TABLE
        String query = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT, MESSAGE, PRICE, IMAGE "
                + "FROM Company_Coupon CP JOIN Coupon C "
                + "ON CP.Coupon_ID = C.ID "
                + "WHERE Comp_ID = ? and TYPE = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, comp.getID());
            ps.setString(2, type.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
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
            throw new GetCouponsOfCompanyByTypeException("Failed to abtain the coupons list.");
        }

        return couponList;

    }

    @Override
    public List<Coupon> getCouponsOfCompanyMaxPrice(Company comp, double price) throws GetCouponsOfCompanyMaxPriceException
    {
        List<Coupon> couponList = new ArrayList<>();

        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e1) {
            throw new GetCouponsOfCompanyMaxPriceException("System was unable to establish"
                    + " a connection.");
        }

        // Get coupons from JOIN TABLE
        String query = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE "
                + "FROM Company_Coupon CP JOIN Coupon C "
                + "ON CP.Coupon_ID = C.ID "
                + "WHERE Comp_ID = ? and PRICE <= ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, comp.getID());
            ps.setDouble(2, price);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
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
            throw new GetCouponsOfCompanyMaxPriceException("Failed to abtain the coupons list.");
        }

        return couponList;

    }

    @Override
    public void removeCouponsOfCompany(Company company) throws RemoveCouponsOfCompanyException, RollbackDataBaseException
    {
        long compID = company.getID();
        String compName = company.getCompName();

        // Create variable for IDs iteration
        long currentID;

        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e1) {
            throw new RemoveCouponsOfCompanyException("System was unable to establish"
                    + " a connection.");
        }

        // Query to get all company's Coupon IDs.
        String getCoupIDsQuery = "SELECT Coupon_ID FROM Company_Coupon WHERE Comp_ID = ?";

        // Queries to delete all company's coupons from all tables
        String customerCouponTableQuery = "DELETE FROM Customer_Coupon WHERE Coupon_ID = ?";
        String couponTableQuery = "DELETE FROM Coupon WHERE ID = ?";
        String companyCouponTableQuery = "DELETE FROM Company_Coupon WHERE Comp_ID = ?";

        try (PreparedStatement getCoupIDsStmt = con.prepareStatement(getCoupIDsQuery);
                PreparedStatement customerCouponTableStmt = con.prepareStatement(customerCouponTableQuery);
                PreparedStatement couponTableStmt = con.prepareStatement(couponTableQuery); 
                PreparedStatement companyCouponTableStmt = con.prepareStatement(companyCouponTableQuery))
        {
            // Get the coupons IDs
            getCoupIDsStmt.setLong(1, compID);
            ResultSet rsID = getCoupIDsStmt.executeQuery();

            con.setAutoCommit(false);

            while (rsID.next())
            {
                currentID = rsID.getLong("Coupon_ID");

                customerCouponTableStmt.setLong(1, currentID);
                customerCouponTableStmt.executeUpdate();

                couponTableStmt.setLong(1, currentID);
                couponTableStmt.executeUpdate();

            }

            companyCouponTableStmt.setLong(1, compID);
            companyCouponTableStmt.executeUpdate();

            con.commit();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error, please contact customer service!");
            }
            throw new RemoveCouponsOfCompanyException("Failed to remove " + compName + " company's coupons.");
        } finally {
            pool.returnConnection(con);
        }
    }

    @Override
    public List<Coupon> getCouponsOfCompanyMaxDate(Company comp, Date date) throws GetCouponsOfCompanyMaxDateException
    {
        List<Coupon> couponList = new ArrayList<>();

        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection();

        } catch (ClassNotFoundException | SQLException e1) {
            throw new GetCouponsOfCompanyMaxDateException("System was unable to establish"
                    + " a connection.");
        }

        // Get coupons from JOIN TABLE
        String query = "SELECT ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE "
                + "FROM Company_Coupon CP JOIN Coupon C "
                + "ON CP.Coupon_ID = C.ID "
                + "WHERE Comp_ID = ? and END_DATE <= ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setLong(1, comp.getID());
            ps.setDate(2, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
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
            throw new GetCouponsOfCompanyMaxDateException("Failed to abtain the coupons list.");
        }

        return couponList;

    }

    @Override
    public boolean couponNameExists(String name) throws GetAllCouponException, CouponNameExistException
    {
        // Create query String
        String query = "SELECT * FROM Coupon WHERE TITLE = ?";

        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new GetAllCouponException("System was unable to establish"
                    + " a connection.");
        }

        // Get result from DB
        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new CouponNameExistException("Error while checking coupon name!");
        }

    }

    // Used by DailyCouponExpirationTask only
    @Override
    public List<Coupon> getOldCoupons() throws CouponNotAvailableException, GetOldCouponsException 
    {
        // constructing a List<Coupon> to return
        List<Coupon> oldCoupons = new ArrayList<Coupon>();

        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e) {
            throw new CouponNotAvailableException("System was unable to establish a connection.");
        }

        String query = "SELECT ID FROM Coupon WHERE ? > END_DATE";
        LocalDate date = LocalDate.now();
        Date dateNow = java.sql.Date.valueOf(date);
        
        try (PreparedStatement pstmt = con.prepareStatement(query))
        {
            pstmt.setDate(1, dateNow);
            ResultSet rs = pstmt.executeQuery();

            Coupon coupon = null;

            while (rs.next()) {
                coupon = new Coupon();
                coupon.setID(rs.getLong("ID"));
                oldCoupons.add(coupon);
            }
        } catch (SQLException e) {
            throw new GetOldCouponsException("Failed to get expired coupons.");
        } finally {
            pool.returnConnection(con);
        }

        return oldCoupons;
    }

}
