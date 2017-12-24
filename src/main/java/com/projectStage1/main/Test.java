//package com.projectStage1.main;
//
//import java.sql.Date;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import com.projectStage1.beans.Company;
//import com.projectStage1.beans.Coupon;
//import com.projectStage1.beans.CouponType;
//import com.projectStage1.beans.Customer;
//import com.projectStage1.exceptions.CompanyCreationException;
//import com.projectStage1.exceptions.CompanyNameExistException;
//import com.projectStage1.exceptions.CompanyNotFoundException;
//import com.projectStage1.exceptions.CompanyRemovalException;
//import com.projectStage1.exceptions.CompanyUpdateException;
//import com.projectStage1.exceptions.CouponCreationException;
//import com.projectStage1.exceptions.CouponNameExistException;
//import com.projectStage1.exceptions.CouponNotAvailableException;
//import com.projectStage1.exceptions.CouponPurchaseException;
//import com.projectStage1.exceptions.CouponRemovalException;
//import com.projectStage1.exceptions.CouponUpdateException;
//import com.projectStage1.exceptions.CustomerCreationException;
//import com.projectStage1.exceptions.CustomerNameExistException;
//import com.projectStage1.exceptions.CustomerNotFoundException;
//import com.projectStage1.exceptions.CustomerRemovalException;
//import com.projectStage1.exceptions.CustomerUpdateException;
//import com.projectStage1.exceptions.GetAllCompaniesException;
//import com.projectStage1.exceptions.GetAllCouponException;
//import com.projectStage1.exceptions.GetAllCustomersException;
//import com.projectStage1.exceptions.GetCouponByCustomerException;
//import com.projectStage1.exceptions.GetCouponOfCustomerByPriceException;
//import com.projectStage1.exceptions.GetCouponOfCustomerByTypeException;
//import com.projectStage1.exceptions.GetCouponsByCompanyException;
//import com.projectStage1.exceptions.GetCouponsOfCompanyByTypeException;
//import com.projectStage1.exceptions.GetCouponsOfCompanyMaxDateException;
//import com.projectStage1.exceptions.GetCouponsOfCompanyMaxPriceException;
//import com.projectStage1.exceptions.GetCouponsOfCustomerException;
//import com.projectStage1.exceptions.GetCustomerByNameException;
//import com.projectStage1.exceptions.GetCustomerException;
//import com.projectStage1.exceptions.LoginException;
//import com.projectStage1.exceptions.PurchaseCouponException;
//import com.projectStage1.exceptions.RemoveCouponsOfCompanyException;
//import com.projectStage1.exceptions.RollbackDataBaseException;
//import com.projectStage1.facades.AdminFacade;
//import com.projectStage1.facades.ClientType;
//import com.projectStage1.facades.CompanyFacade;
//import com.projectStage1.facades.CouponClientFacade;
//import com.projectStage1.facades.CustomerFacade;
//
//public class Test {
//
//    private static AdminFacade adminFacade;
//    private static CompanyFacade companyFacade;
//    private static CustomerFacade customerFacade;
//    private static CouponClientFacade mainFacade;
//    
//    public static void main(String[] args)
//    {
//        String username = "admin";
//        String password = "1234";
//        ClientType clientType = ClientType.ADMIN;
//        
//        Scanner scan = new Scanner(System.in);
//        
//        System.out.println("Welcome to Valentine & Shay demo application of Coupon Management System.\n"
//                         + "All you have to do is to read the messages\n"
//                         + "and hit enter when needed.\n\n"
//                         + "~To begin please hit enter.");
//        scan.nextLine();
//
//        //############## Admin login ###############
//        System.out.println("~To login as Administrator hit enter (default username and password are \"admin\" and \"1234\").");
//        scan.nextLine();
//        // Login to System
//        try {
//            mainFacade = CouponSystem.getInstance().login(username, password, clientType);
//            System.out.println("Login success!");
//        } catch (LoginException | CompanyNotFoundException | GetCustomerByNameException | CustomerNotFoundException e) {
//            System.err.println("Your user-name or password is incorrect. PLease check and try again.");
//        }
//        
//        // Set suitable facade to use it's methods
//        setFacade(clientType);
//        
//        // Create company
//        System.out.println("\n~To create new company \"Samsung\" with password \"samsung\" "
//                + "and email \"address@samsung.com\" please hit enter.");
//        scan.nextLine();
//        
//        // Create company object
//        Company comp1 = new Company("Samsung", "samsung", "address@samsung.com");
//        
//        // Add to Model (MVC)
//        try {
//            adminFacade.createCompany(comp1);
//        } catch (CompanyCreationException | GetAllCompaniesException | GetAllCouponException
//                | CompanyNameExistException | RollbackDataBaseException e) {
//            System.err.println(e.getMessage());
//        }
//        
//        // View all companies
//        System.out.println("\n~To view created companies hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Company> compList = (ArrayList<Company>) adminFacade.getAllCompanies();
//            for (Company c : compList){
//                System.out.println("Company name: " + c.getCompName() + "\n"
//                                 + "ID:           " + c.getID() + "\n"
//                                 + "Email:        " + c.getEmail() + "\n");
//            }
//        } catch (GetAllCompaniesException e) {
//            System.err.println(e.getMessage());
//        }
//        
//        // Update company
//        System.out.println("\n~To update Samsung's email to the new one hit enter.");
//        scan.nextLine();
//                
//        try {
//            comp1.setEmail("new_email@samsung.com");
//            adminFacade.updateCompany(comp1);
//        } catch (CompanyUpdateException | CompanyNotFoundException | RollbackDataBaseException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        //
//        System.out.println("\n~To view the updated company info hit enter.");
//        scan.nextLine();
//        
//        try {
//            System.out.println(adminFacade.getCompany(comp1.getID()));
//        } catch (CompanyNotFoundException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        //Create customer
//        System.out.println("\n~To create customer Assaf with password \"asfasf\" hit enter");
//        scan.nextLine();
//        // Create customer object
//        Customer cust1 = new Customer("Assaf", "asfasf");
//        
//        try {
//            adminFacade.createCustomer(cust1);
//        } catch (CustomerNameExistException | CustomerCreationException | GetAllCustomersException
//                | RollbackDataBaseException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View all customers
//        System.out.println("\n~To view all customers hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Customer> custList = (ArrayList<Customer>) adminFacade.getAllCustomers();
//            for (Customer c : custList)
//            {
//                System.out.println("Customer name: " + c.getCustName());
//                System.out.println("ID:            " + c.getID());
//                System.out.println("password:      " + c.getPassword());
//             
//            }
//        
//        } catch (GetAllCustomersException e2) {
//            System.err.println(e2.getMessage());
//        }
//                
//        // Update customer
//        System.out.println("\n~To update customer's password to \"taarikhLeida\" hit enter.");
//        scan.nextLine();
//        
//        cust1.setPassword("taarikhLeida");
//        try {
//            adminFacade.updateCustomer(cust1);
//        } catch (CustomerNameExistException | GetAllCustomersException | CustomerUpdateException
//                | RollbackDataBaseException | GetCustomerException | CustomerNotFoundException e2) {
//            System.err.println(e2.getMessage());
//        }
//        
//        // Get updated customer
//        System.out.println("\n~To view the updated customer info hit enter.");
//        scan.nextLine();
//        
//        try {
//            System.out.println(adminFacade.getCustomer(cust1.getID()));
//        } catch (GetCustomerException | CustomerNotFoundException e2) {
//            System.err.println(e2.getMessage());
//        }
//        
//        //############# Company login #############
//        
//        username = comp1.getCompName();
//        password = comp1.getPassword();
//        clientType = ClientType.COMPANY;
//
//        System.out.println("\n~To login as \"Samsung\" Company hit enter.");
//        scan.nextLine();
//        // Login to System
//        try {
//            mainFacade = CouponSystem.getInstance().login(username, password, clientType);
//            System.out.println("Login success.");
//        } catch (LoginException | CompanyNotFoundException | GetCustomerByNameException | CustomerNotFoundException e) {
//            System.err.println("Your user-name or password is incorrect. PLease check and try again.");
//        }
//        
//        // Set suitable facade to use it's methods
//        setFacade(clientType);
//        
//        // Set facade company
////        companyFacade.setCompany(comp1);
//
//        // Create coupon
//        System.out.println("\n~To create coupon of Galaxy S6 hit enter.");
//        scan.nextLine();
//        
//        Date startDate = java.sql.Date.valueOf("2014-05-16");
//        Date endDate = java.sql.Date.valueOf("2017-12-31");
//        Coupon coup1 = new Coupon("Galaxy S6", startDate, endDate, 658,
//                CouponType.ELECTRONICS, "Best phone ever", 700d, "noImg.jpg");
//
//        try {
//            companyFacade.createCoupon(coup1);
//        } catch (CouponCreationException | GetAllCouponException | CouponNameExistException
//                | RollbackDataBaseException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View all coupons
//        System.out.println("\n~To view all created coupons hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Coupon> couponList = (ArrayList<Coupon>) companyFacade.getAllCoupons();
//            
//            if (couponList.isEmpty()) {
//                System.out.println("No data to show.");
//            } else {
//                for (Coupon c : couponList) {
//                    System.out.println("Coupon ID:  " + c.getID());
//                    System.out.println("title:      " + c.getTitle());
//                    System.out.println("type:       " + c.getType());
//                    System.out.println("price:      " + c.getPrice());
//                    System.out.println("start date: " + c.getStartDate());
//                    System.out.println("end date:   " + c.getEndDate());
//                    System.out.println("amount:     " + c.getAmount());
//                    System.out.println("message:    " + c.getMessage());
//                    System.out.println("image:      " + c.getImage());
//                } 
//            }
//        
//        } catch (CompanyNotFoundException | GetCouponsByCompanyException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // Update coupon
//        System.out.println("\n~To update the coupon with new price hit enter.");
//        scan.nextLine();
//        
//        coup1.setPrice(599.99);
//        try {
//            companyFacade.updateCoupon(coup1);
//        } catch (CouponUpdateException | GetAllCouponException | CouponNameExistException
//                | RollbackDataBaseException | CouponNotAvailableException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View updated coupon
//        System.out.println("\n~To view the updated coupon info hit enter.");
//        scan.nextLine();
//        
//        try {
//            System.out.println(companyFacade.getCoupon(coup1.getID()));
//        } catch (CouponNotAvailableException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View coupons by type
//        System.out.println("\n~To view all coupons of type \"electronics\" hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Coupon> couponList = (ArrayList<Coupon>) companyFacade.getCouponsByType(CouponType.ELECTRONICS);
//            for (Coupon c : couponList)
//            {
//                System.out.println("Coupon ID:  " + c.getID());
//                System.out.println("title:      " + c.getTitle());
//                System.out.println("type:       " + c.getType());
//                System.out.println("price:      " + c.getPrice());
//                System.out.println("start date: " + c.getStartDate());
//                System.out.println("end date:   " + c.getEndDate());
//                System.out.println("amount:     " + c.getAmount());
//                System.out.println("message:    " + c.getMessage());
//                System.out.println("image:      " + c.getImage());
//            }
//        } catch (GetCouponsOfCompanyByTypeException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View coupons by price
//        System.out.println("\n~To view all coupons of the company under $1000 hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Coupon> couponList = (ArrayList<Coupon>) companyFacade.getCouponsMaxPrice(1000.0);
//            for (Coupon c : couponList)
//            {
//                System.out.println("Coupon ID:  " + c.getID());
//                System.out.println("title:      " + c.getTitle());
//                System.out.println("type:       " + c.getType());
//                System.out.println("price:      " + c.getPrice());
//                System.out.println("start date: " + c.getStartDate());
//                System.out.println("end date:   " + c.getEndDate());
//                System.out.println("amount:     " + c.getAmount());
//                System.out.println("message:    " + c.getMessage());
//                System.out.println("image:      " + c.getImage());
//            }
//        } catch (GetCouponsOfCompanyMaxPriceException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View coupons by date
//        System.out.println("\n~To view coupons that will expire till 2018 hit enter.");
//        scan.nextLine();
//        
//        Date date = java.sql.Date.valueOf("2018-01-01");
//        
//        try {
//            ArrayList<Coupon> couponList = (ArrayList<Coupon>) companyFacade.getCouponsMaxDate(date);
//            for (Coupon c : couponList)
//            {
//                System.out.println("Coupon ID:  " + c.getID());
//                System.out.println("title:      " + c.getTitle());
//                System.out.println("type:       " + c.getType());
//                System.out.println("price:      " + c.getPrice());
//                System.out.println("start date: " + c.getStartDate());
//                System.out.println("end date:   " + c.getEndDate());
//                System.out.println("amount:     " + c.getAmount());
//                System.out.println("message:    " + c.getMessage());
//                System.out.println("image:      " + c.getImage());
//            }
//        } catch (GetCouponsOfCompanyMaxDateException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        //############### Customer login ##############
//        
//        username = cust1.getCustName();
//        password = cust1.getPassword();
//        clientType = ClientType.CUSTOMER;
//
//        System.out.println("\n~To login as Assaf customer hit enter.");
//        scan.nextLine();
//        // Login to System
//        try {
//            mainFacade = CouponSystem.getInstance().login(username, password, clientType);
//            System.out.println("Login success.");
//        } catch (LoginException | CompanyNotFoundException | GetCustomerByNameException | CustomerNotFoundException e) {
//            System.err.println("Your user-name or password is incorrect. PLease check and try again.");
//        }
//        
//        // Set suitable facade to use it's methods
//        setFacade(clientType);
//        
//        // Set facade customer
////        customerFacade.setThisCust(cust1);
//        
//        // Purchase Coupon
//        System.out.println("\n~To purchase Galaxy S6 hit enter.");
//        scan.nextLine();
//        
//        try {
//            customerFacade.purchaseCoupon(coup1);
//        } catch (CustomerCreationException | PurchaseCouponException | RollbackDataBaseException | GetCouponsOfCustomerException | GetCouponByCustomerException | CouponPurchaseException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View all purchased coupons
//        System.out.println("\n~To view all purchased coupons hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Coupon> coupList = (ArrayList<Coupon>) customerFacade.getAllPurchasedCoupons();
//            for (Coupon c : coupList)
//            {
//                System.out.println("Coupon ID:  " + c.getID());
//                System.out.println("title:      " + c.getTitle());
//                System.out.println("type:       " + c.getType());
//                System.out.println("price:      " + c.getPrice());
//                System.out.println("start date: " + c.getStartDate());
//                System.out.println("end date:   " + c.getEndDate());
//                System.out.println("amount:     " + c.getAmount());
//                System.out.println("message:    " + c.getMessage());
//                System.out.println("image:      " + c.getImage());
//            }
//        } catch (GetCouponsOfCustomerException | GetCouponByCustomerException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View purchased coupons of type
//        System.out.println("\n~To view all purchased coupons of type \"electronics\" hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Coupon> coupList = (ArrayList<Coupon>) customerFacade.getAllPurchasedCouponsByType(CouponType.ELECTRONICS);
//            for (Coupon c : coupList)
//            {
//                System.out.println("Coupon ID:  " + c.getID());
//                System.out.println("title:      " + c.getTitle());
//                System.out.println("type:       " + c.getType());
//                System.out.println("price:      " + c.getPrice());
//                System.out.println("start date: " + c.getStartDate());
//                System.out.println("end date:   " + c.getEndDate());
//                System.out.println("amount:     " + c.getAmount());
//                System.out.println("message:    " + c.getMessage());
//                System.out.println("image:      " + c.getImage());
//            }
//        } catch (GetCouponOfCustomerByTypeException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View purchased coupons by price
//        System.out.println("\n~To view all purchased coupons under $1000 hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Coupon> coupList = (ArrayList<Coupon>) customerFacade.getAllPurchasedCouponsByPrice(1000.0);
//            for (Coupon c : coupList)
//            {
//                System.out.println("Coupon ID:  " + c.getID());
//                System.out.println("title:      " + c.getTitle());
//                System.out.println("type:       " + c.getType());
//                System.out.println("price:      " + c.getPrice());
//                System.out.println("start date: " + c.getStartDate());
//                System.out.println("end date:   " + c.getEndDate());
//                System.out.println("amount:     " + c.getAmount());
//                System.out.println("message:    " + c.getMessage());
//                System.out.println("image:      " + c.getImage());
//            }
//        } catch (GetCouponOfCustomerByPriceException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        //############### Back to company facade #############
//        
//        System.out.println("\n####### We're back in company facade ######\n"
//                + "------------------------------------------");
//        
//        // Remove coupon
//        System.out.println("\n~To remove a created coupon hit enter.");
//        scan.nextLine();
//        
//        try {
//            companyFacade.removeCoupon(coup1);
//        } catch (CouponRemovalException | RollbackDataBaseException | CouponNotAvailableException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View all coupons
//        System.out.println("\n~To view all company's coupons hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Coupon> couponList = (ArrayList<Coupon>) companyFacade.getAllCoupons();
//            for (Coupon c : couponList)
//            {
//                System.out.println("Coupon ID:  " + c.getID());
//                System.out.println("title:      " + c.getTitle());
//                System.out.println("type:       " + c.getType());
//                System.out.println("price:      " + c.getPrice());
//                System.out.println("start date: " + c.getStartDate());
//                System.out.println("end date:   " + c.getEndDate());
//                System.out.println("amount:     " + c.getAmount());
//                System.out.println("message:    " + c.getMessage());
//                System.out.println("image:      " + c.getImage());
//            }
//        
//        } catch (CompanyNotFoundException | GetCouponsByCompanyException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        //############## Back to admin facade #############
//        
//        System.out.println("\n###### We're back in admin facade ######\n"
//                + "-----------------------------");
//        
//        // Remove customer
//        System.out.println("\n~To remove the customer hit enter.");
//        scan.nextLine();
//        
//        try {
//            adminFacade.removeCustomer(cust1);
//        } catch (CustomerRemovalException | RollbackDataBaseException | GetCustomerException | CustomerNotFoundException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View all customers
//        System.out.println("\n~To view all customers hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Customer> custList = (ArrayList<Customer>) adminFacade.getAllCustomers();
//            for (Customer c : custList)
//            {
//                System.out.println("Customer name: " + c.getCustName());
//                System.out.println("ID:            " + c.getID());
//                System.out.println("password:      " + c.getPassword());
//             
//            }
//        
//        } catch (GetAllCustomersException e2) {
//            System.err.println(e2.getMessage());
//        }
//        
//        // Remove company
//        System.out.println("\n~To remove Samsung company hit enter.");
//        scan.nextLine();
//        
//        try {
//            adminFacade.removeCompany(comp1);
//        } catch (CompanyRemovalException | RemoveCouponsOfCompanyException | RollbackDataBaseException | CompanyNotFoundException e1) {
//            System.err.println(e1.getMessage());
//        }
//        
//        // View all companies
//        System.out.println("\n~To view all companies hit enter.");
//        scan.nextLine();
//        
//        try {
//            ArrayList<Company> compList = (ArrayList<Company>) adminFacade.getAllCompanies();
//            for (Company c : compList){
//                System.out.println("Company name: " + c.getCompName() + "\n"
//                                 + "ID:           " + c.getID() + "\n"
//                                 + "Email:        " + c.getEmail() + "\n");
//            }
//        } catch (GetAllCompaniesException e) {
//            System.err.println(e.getMessage());
//        }
//    
//        System.out.println("\n~End of demo. Please hit enter to exit the system.");
//        scan.nextLine();
//        scan.close();
//        try {
//            CouponSystem.getInstance().shutdown();
//            System.out.println("\n~Good Bye!");
//        } catch (ClassNotFoundException | SQLException e) {
//            System.err.println("Exit error. System was not closed properly.");
//        }
//    
//    }
//    
//    // Assigns a suitable facade object to access it's methods
//    private static void setFacade(ClientType clientType)
//    {
//        //Admin
//        if (clientType.equals(ClientType.ADMIN))
//        {
//            adminFacade = (AdminFacade)mainFacade;
//        // Company
//        } else if (clientType.equals(ClientType.COMPANY))
//        {
//            companyFacade = (CompanyFacade)mainFacade;
//        // Customer
//        } else if (clientType.equals(ClientType.CUSTOMER))
//        {
//            customerFacade = (CustomerFacade)mainFacade;
//        }
//    }    
//}
