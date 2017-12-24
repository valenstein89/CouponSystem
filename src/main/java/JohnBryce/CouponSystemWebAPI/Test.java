package JohnBryce.CouponSystemWebAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.projectStage1.connectionPool.ConnectionPool;

public class Test {

    public static void main(String[] args) {
    	
    	try {
			int compCount = 0;
			
			Connection con = ConnectionPool.getInstance().getConnection();
			
			String query = "select * from company";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				System.out.println(rs.getString("comp_name"));				
				compCount++;
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
//    	try {
//			AdminFacade adFac = (AdminFacade)CouponSystem.getInstance().login("admin", "1234", ClientType.ADMIN);
//			
//			List<Company> compList = adFac.getAllCompanies();
//			
//			for (Company c : compList) {
//				System.out.println(c);
//			}
//		} catch (LoginException | CompanyNotFoundException | GetCustomerByNameException | CustomerNotFoundException
//				| GetAllCompaniesException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    }

}
