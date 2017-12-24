package com.CouponSystemCore.beans;

import java.util.List;

import com.CouponSystemCore.util.IDGenerator;

public class Company 
{
	private long ID;
	private String compName;
	private String password;
	private String email;
	private List<Coupon> coupons;
	
	public Company()
	{		
//		//Generate new random ID
//		ID = IDGenerator.makeNewID();
	}
	
	public Company(String compName, String password, String email)
	{
//		this();
		
		setCompName(compName);
		setPassword(password);
		setEmail(email);
		//TODO initialize Collection<Coupon> coupons
	}
	
	public Company(long ID, String compName, String password, String email)
	{
		this(compName, password, email);	
		setID(ID);
	}
	
	public long getID() {
		return ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public List<Coupon> getCoupons(){
		return coupons;
	}

	@Override
	public String toString() {
		return "Company [ID=" + ID + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}

}
