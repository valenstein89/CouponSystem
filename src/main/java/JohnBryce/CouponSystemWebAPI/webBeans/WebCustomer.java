package JohnBryce.CouponSystemWebAPI.webBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.projectStage1.beans.Customer;
import com.projectStage1.util.IDGenerator;

@XmlRootElement
public class WebCustomer implements Serializable
{
        private long ID;
        private String custName;
        private String password;
        private List<WebCoupon> coupons;
        
        public WebCustomer(){
//                // Assign unique ID
//                ID = IDGenerator.makeNewID();
        }
        
        public WebCustomer(long ID) {
            setID(ID);
        }
        
        public WebCustomer(String custName, String password)
        {
//                // Go to empty constructor to get
//                // a unique ID
//                this();
                
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
        public WebCustomer(long ID, String custName, String password)
        {       
                // Use existing constructor
                this(custName, password);
                
                // Overwrite automatically assigned ID with another
                this.ID = ID;
        }
        
        public WebCustomer(Customer cust) {
            setID(cust.getID());
            setCustName(cust.getCustName());
            setPassword(cust.getPassword());
            setCoupons(WebCoupon.convertToWebCoupons(cust.getCoupons()));
        }
        
        public static List<WebCustomer> convertToWebCustomers(List<Customer> custList) {
            List<WebCustomer> result = new ArrayList<>();
            
            if (custList != null) {
                for (Customer c : custList) {
                    result.add(new WebCustomer(c));
                } 
            }
            
            return result;
        }
        
        public Customer convertToCustomer() {
            return new Customer(getID(), getCustName(), getPassword());
        }
        
        public static List<Customer> convertToCustomers(List<WebCustomer> webCustList){
            List<Customer> result = new ArrayList<>();
            if (webCustList != null) {
                for (WebCustomer wc : webCustList) {
                    result.add(wc.convertToCustomer());
                }
            }
            return result;
        }
        
        public void setID(long ID) {
                this.ID = ID;
        }

        public void setCoupons(List<WebCoupon> coupons) {
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

        public List<WebCoupon> getCoupons() {
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
