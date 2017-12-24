package com.CouponSystemWebAPI.login.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.CouponSystemCore.exceptions.CompanyNotFoundException;
import com.CouponSystemCore.exceptions.CustomerNotFoundException;
import com.CouponSystemCore.exceptions.GetCustomerByNameException;
import com.CouponSystemCore.exceptions.LoginException;
import com.CouponSystemCore.facades.AdminFacade;
import com.CouponSystemCore.facades.ClientType;
import com.CouponSystemCore.facades.CompanyFacade;
import com.CouponSystemCore.facades.CouponClientFacade;
import com.CouponSystemCore.facades.CustomerFacade;
import com.CouponSystemCore.main.CouponSystem;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// this is how to get the input text data in the servlet
		String username = request.getParameter("username");
		String clientTypeRaw = request.getParameter("clientType");
		ClientType clientType = ClientType.valueOf(clientTypeRaw.toUpperCase());
		String password = request.getParameter("password");

		// perform login
		CouponClientFacade facade;
		try {
			
			// Perfoming a login operation 
			facade = CouponSystem.getInstance().login(username, password, clientType);

			// Creating a session 
			HttpSession session = request.getSession();
			
			// place the facade on the Session
			session.setAttribute("facade", facade);

			// now you can redirect to the correct page (if the login was successful)
			if(facade instanceof AdminFacade) {
				response.sendRedirect("admin/admin.html");
			} else if(facade instanceof CompanyFacade) {
				response.sendRedirect("company/company.html");
			} else if(facade instanceof CustomerFacade) {
				response.sendRedirect("customer/customer.html");
			}

		} catch (LoginException | CompanyNotFoundException | GetCustomerByNameException | CustomerNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Set response content type
			String contextPathUrl = request.getContextPath();
			
		      response.setContentType("text/html");
		 
		      PrintWriter out = response.getWriter();
		      out.write("<html><head><title>Unsuccessful login.</title></head><body>");
		      out.write("<p><h3>You entered a wrong username or password.</h3></p></br>");		      
		      out.write("<p><h3>Please recheck and try again.</h3></p></br>");		      
		      out.write("<a href=\"" + contextPathUrl + "/login.html\">Back to Login Page</a>");		      
		      out.write("</body></html>");
			
		}
	}
}