package com.projectStage1.beans;

import java.util.List;

import com.projectStage1.util.IDGenerator;

public class Customer 
{
	private long ID;
	private String custName;
	private String password;
	private List<Coupon> coupons;
	
	public Customer(){
//		// Assign unique ID
//		ID = IDGenerator.makeNewID();
	}
	
	public Customer(String custName, String password)
	{
//		// Go to empty constructor to get
//		// a unique ID
//		this();
		
		this.custName = custName;
		this.password = password;
	}
	
	
	/**
	 * Used to create customer with given ID as opposed to
	 * the second constructor, that assigns ID automatically.
	 * @param ID 
	 * @param custName
	 * @param password
	 */
	public Customer(long ID, String custName, String password)
	{	
		// Use existing constructor
		this(custName, password);
		
		// Overwrite automatically assigned ID with another
		this.ID = ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public long getID() {
		return ID;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	@Override
	public String toString() {
		return "Customer [ID="       + ID 
		             + ", custName=" + custName 
		             + ", password=" + password 
		             + ", coupons="  + coupons + "]";
	}
	
}
