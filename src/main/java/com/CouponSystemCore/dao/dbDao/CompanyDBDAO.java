package com.CouponSystemCore.dao.dbDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.CouponSystemCore.beans.Company;
import com.CouponSystemCore.connectionPool.ConnectionPool;
import com.CouponSystemCore.dao.CompanyDAO;
import com.CouponSystemCore.exceptions.CompanyCreationException;
import com.CouponSystemCore.exceptions.CompanyNameExistException;
import com.CouponSystemCore.exceptions.CompanyNotFoundException;
import com.CouponSystemCore.exceptions.CompanyRemovalException;
import com.CouponSystemCore.exceptions.CompanyUpdateException;
import com.CouponSystemCore.exceptions.GetAllCompaniesException;
import com.CouponSystemCore.exceptions.GetAllCouponException;
import com.CouponSystemCore.exceptions.LoginException;
import com.CouponSystemCore.exceptions.RollbackDataBaseException;

public class CompanyDBDAO implements CompanyDAO
{
    private static ConnectionPool pool;

    @Override
    public void createCompany(Company company) throws RollbackDataBaseException, CompanyCreationException
    {
        String companyName = company.getCompName();

        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new CompanyCreationException("System was unable to establish"
                    + " a connection to create company: " + companyName);
        }

//        //SQL query for Coupon table
//        String query = "INSERT INTO Company (ID, COMP_NAME, PASSWORD, EMAIL)"
//                + " VALUES (?,?,?,?)";
        //SQL query for Coupon table
        String query = "INSERT INTO Company (COMP_NAME, PASSWORD, EMAIL)"
        		+ " VALUES (?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            // create a prepared statement
//            ps.setLong(1, company.getID());
//            ps.setString(2, companyName);
//            ps.setString(3, company.getPassword());
//            ps.setString(4, company.getEmail());
            ps.setString(1, companyName);
            ps.setString(2, company.getPassword());
            ps.setString(3, company.getEmail());
            
            // execute statement
            ps.executeUpdate();

        } catch (SQLException e) {
//            try {
//                if (con != null){
//                    con.rollback();
//                }
//            } catch (SQLException e1) {
//                throw new RollbackDataBaseException("System error. Please contact customer service.");
//            }
            throw new CompanyCreationException("Failed to create company " + companyName);
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public void removeCompany(Company company) throws CompanyRemovalException, RollbackDataBaseException
    {
        long id = company.getID();

        //Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new CompanyRemovalException("System was unable to establish"
                    + " a connection to remove company: " + company.getCompName());
        }

        String companyTableQuery = "DELETE FROM Company WHERE ID = ?";

        try (PreparedStatement companyTableStmt = con.prepareStatement(companyTableQuery))
        {
            companyTableStmt.setLong(1, id);
            companyTableStmt.executeUpdate();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error. Please contact customer service.");
            }
            throw new CompanyRemovalException("Failed to remove company " + company.getCompName());
        } finally {
            pool.returnConnection(con);
        }

    }

    @Override
    public void updateCompany(Company company) throws CompanyUpdateException, RollbackDataBaseException
    {
        String companyName = company.getCompName();

        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new CompanyUpdateException("System was unable to establish"
                    + " a connection to update company: " + companyName);
        }

        String query = "UPDATE Company SET COMP_NAME = ?, PASSWORD = ?, EMAIL = ? WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, companyName);
            ps.setString(2, company.getPassword());
            ps.setString(3, company.getEmail());
            ps.setLong(4, company.getID());

            ps.executeUpdate();

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new RollbackDataBaseException("System error. Please contact customer service.");
            }
            throw new CompanyUpdateException("Failed to update company " + companyName);
        } finally {
            pool.returnConnection(con);
        }

    }

	@Override
	public Company getCompany(long ID) throws CompanyNotFoundException 
	{
		Company result = null;
		/////////////////////////////////////////////////////////////////////
//		List<Coupon> coupList = new ArrayList<>();
		////////////////////////////////////////////////////////////////////
		
		Connection con;

		try 
		{
			pool = ConnectionPool.getInstance();
			con = pool.getConnection(); 

		} catch (ClassNotFoundException | SQLException e2) {
			throw new CompanyNotFoundException("System was unable to establish"
					+ " a connection to obtain company with ID: " + ID);
		}

		String query = "SELECT * FROM Company WHERE ID = ?";
		//////////////////////////////////////////////////////////////////////
//		String coupQuery = "SELECT * FROM Company_Coupon WHERE Comp_ID = ?";
		//////////////////////////////////////////////////////////////////////
		
		try (PreparedStatement ps = con.prepareStatement(query)
			//////////////////////////////////////////////////////////////////////
//			PreparedStatement coupPs = con.prepareStatement(coupQuery))
			//////////////////////////////////////////////////////////////////////
				)
		{
			ps.setLong(1, ID);
			ResultSet rs = ps.executeQuery();

			if (rs.next())
			{
				result = new Company();
				result.setID(ID);

				result.setCompName(rs.getString("COMP_NAME"));
				result.setPassword(rs.getString("PASSWORD"));
				result.setEmail(rs.getString("EMAIL"));
			} else {
				throw new CompanyNotFoundException();
			}
			
			//////////////////////////////////////////////////////////////////////
//			coupPs.setLong(1,ID);
//			ResultSet coupRs = coupPs.executeQuery();
//			
//			while (coupRs.next()){
//				Coupon coup = new Coupon();
//				
//				coup.setID(coupRs.getLong("ID"));
//				coup.setTitle(coupRs.getString("TITLE"));
//				coup.setStartDate(coupRs.getDate("START_DATE"));
//				coup.setEndDate(coupRs.getDate("END_DATE"));
//				coup.setAmount(coupRs.getInt("AMOUNT"));
//				coup.setType(CouponType.valueOf(coupRs.getString("TYPE").toUpperCase()));
//				coup.setMessage(coupRs.getString("MESSAGE"));
//				coup.setPrice(coupRs.getDouble("PRICE"));
//				coup.setImage(coupRs.getString("IMAGE"));
//			
//				coupList.add(coup);
//			}
//			
//			result.setCoupons(coupList);
			//////////////////////////////////////////////////////////////////////
			
		} catch (CompanyNotFoundException e)
		{
			throw new CompanyNotFoundException("Company with ID: " + ID + " does not exist!");
		} catch (SQLException e) {
			throw new CompanyNotFoundException("Failed to get company.");
		} finally {
			pool.returnConnection(con);
		}

		return result;

	}

    @Override
    public Company getCompanyByName(String companyName) throws CompanyNotFoundException 
    {
        Company company = null;

        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new CompanyNotFoundException("System was unable to establish a connection.");
        }

        String query = "SELECT * FROM Company WHERE COMP_NAME = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, companyName);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                company = new Company();
                company.setID(rs.getLong("ID"));
                company.setCompName(companyName);
                company.setPassword(rs.getString("PASSWORD"));
                company.setEmail(rs.getString("EMAIL"));
            } else {
                throw new CompanyNotFoundException();
            }

        } catch (CompanyNotFoundException e)
        {
            throw new CompanyNotFoundException("Company " + companyName + " does not exist!");
        } catch (SQLException e) {
            throw new CompanyNotFoundException("Failed to get company.");
        } finally {
            pool.returnConnection(con);
        }

        return company;

    }

    @Override
    public List<Company> getAllCompanies() throws GetAllCompaniesException 
    {
        List<Company> companyList = new ArrayList<>();
        
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new GetAllCompaniesException("System was unable to establish"
                    + " a connection to obtain list of companies!");
        }

        String query = "SELECT * FROM Company";

        try (Statement stmt = con.createStatement())
        {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                Company company = new Company();
                
                company.setID(rs.getLong("ID"));
                company.setCompName(rs.getString("COMP_NAME"));
                company.setPassword(rs.getString("PASSWORD"));
                company.setEmail(rs.getString("EMAIL"));

                companyList.add(company);
            }
        } catch (SQLException e) {
            throw new GetAllCompaniesException("Failed to get companies list!");
        } finally {
            pool.returnConnection(con);
        }

        return companyList;
    }

    @Override
    public boolean companyNameExists(String name) throws GetAllCouponException, CompanyNameExistException
    {
        // Create query String
        String query = "SELECT * FROM Company WHERE COMP_NAME = ?";
        // Get connection from pool
        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new GetAllCouponException("System was unable to establish"
                    + " a connection!");
        }

        // Get result from DB
        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new CompanyNameExistException("Error while checking company name!");
        }

    }

    @Override
    public boolean login(String companyName, String password) throws LoginException {

        String resultPassword = "";

        Connection con;

        try 
        {
            pool = ConnectionPool.getInstance();
            con = pool.getConnection(); 

        } catch (ClassNotFoundException | SQLException e2) {
            throw new LoginException("System was unable to establish"
                    + " a connection to login!");
        }


        String query = "SELECT PASSWORD FROM Company WHERE COMP_NAME = ?";

        try (PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setString(1, companyName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                resultPassword = rs.getString("PASSWORD");
            } else {
                throw new LoginException("No info about that company in the DB.");
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
