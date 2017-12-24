package com.projectStage1;

import java.sql.SQLException;
import java.util.List;

import com.projectStage1.main.CouponSystem;

import JohnBryce.CouponSystemWebAPI.resources.AdminResource;
import JohnBryce.CouponSystemWebAPI.webBeans.WebCompany;

public class TestTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		AdminResource adRec = new AdminResource();

		WebCompany wc = new WebCompany("TestCompany2", "1234", "test2@email.com", null);

		adRec.createCompany(wc);
		
//		List<WebCompany> webCompList = adRec.getAllCompanies();
//		
//		System.out.println(webCompList);
		
//		adRec.removeCompany(8443575767263056468L);

		CouponSystem.getInstance().shutdown();

	}

}
