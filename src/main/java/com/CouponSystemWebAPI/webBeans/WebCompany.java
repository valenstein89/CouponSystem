package com.CouponSystemWebAPI.webBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.CouponSystemCore.beans.Company;
import com.CouponSystemCore.beans.Coupon;
import com.CouponSystemCore.util.IDGenerator;

@XmlRootElement
public class WebCompany implements Serializable
{
        private long ID;
        private String compName;
        private String password;
        private String email;
        private List<WebCoupon> coupons;
        
        public WebCompany()
        {               
//                //Generate new random ID
//                ID = IDGenerator.makeNewID();
        }
        
        public WebCompany(long ID) {
            setID(ID);
        }
        
        public WebCompany(String compName, String password, String email, List<Coupon> coupons)
        {
//                this();
                
                setCompName(compName);
                setPassword(password);
                setEmail(email);
                setCoupons(WebCoupon.convertToWebCoupons(coupons));
                //TODO initialize Collection<Coupon> coupons
        }
        
        public WebCompany(long ID, String compName, String password, String email, List<Coupon> coupons)
        {
                this(compName, password, email, coupons);        
                setID(ID);
        }
        
        public WebCompany(Company c) {
            setID(c.getID());
            setCompName(c.getCompName());
            setPassword(c.getPassword());
            setEmail(c.getEmail());
            setCoupons(WebCoupon.convertToWebCoupons(c.getCoupons()));
        }
        
        public static List<WebCompany> convertToWebCompanies(List<Company> compList){
            List<WebCompany> result = new ArrayList<>();
            
            if (compList != null) {
                for (Company c : compList) {
                    result.add(new WebCompany(c));
                }
            }
            return result;
        }
        
        public Company convertToCompany(){
            return new Company(getID(), getCompName(), getPassword(), getEmail());
        }
        
        public static List<Company> convertToCompanies(List<WebCompany> webCompList){
            List<Company> result = new ArrayList<>();
            
            if (webCompList != null) {
                for (WebCompany wc : webCompList) {
                    result.add(wc.convertToCompany());
                }
            }
            return result;
        }
        
        public long getID() {
                return ID;
        }

        public void setID(long ID) {
                this.ID = ID;
        }

        public void setCoupons(List<WebCoupon> coupons) {
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
        
        public List<WebCoupon> getCoupons(){
                return coupons;
        }

		@Override
		public String toString() {
			return "WebCompany [ID=" + ID + ", compName=" + compName + ", password=" + password + ", email=" + email
					+ ", coupons=" + coupons + "]";
		}

}

