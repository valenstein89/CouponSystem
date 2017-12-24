package JohnBryce.CouponSystemWebAPI.webBeans;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.projectStage1.beans.Coupon;
import com.projectStage1.beans.CouponType;
import com.projectStage1.util.IDGenerator;

@XmlRootElement
public class WebCoupon implements Serializable{

    private long ID;
    private String title;
    private String startDate;
    private String endDate;
    private int amount;
    private String type;
    private String message;
    private double price;
    private String image;

    public WebCoupon()
    {
//    	  //Creates a new random ID number
//        ID = IDGenerator.makeNewID();
    }
    
    public WebCoupon(long ID) {
        setID(ID);
    }

    /**
     * This Constructor creates a brand new Coupon
     * with new unique ID number.
     */
    public WebCoupon(String title, String startDate, String endDate,
            int amount, String type, String message,
            double price, String image)
    {
        // Uses default constructor
        // to obtain new unique ID
//        this();

        setTitle(title);
        setStartDate(startDate);
        setEndDate(endDate);
        setAmount(amount);
        setType(type);
        setMessage(message);
        setPrice(price);
        setImage(image);
    }

    /**
     * This Constructor creates a Coupon object using an existing ID number.
     */
    public WebCoupon(long ID, String title, String startDate,
            String endDate, int amount, String type,
            String message, double price, String image)
    {
        this(title, startDate, endDate, amount, type, message, price, image);

        setID(ID); //Uses an existing ID number from DB or other source.
    }
    
    public WebCoupon(Coupon c) {
        setID(c.getID());
        setTitle(c.getTitle());
        setStartDate(c.getStartDate().toString());
        setEndDate(c.getEndDate().toString());
        setAmount(c.getAmount());
        setType(c.getType().name());
        setMessage(c.getMessage());
        setPrice(c.getPrice());
        setImage(c.getImage());
    }
    
    public static List<WebCoupon> convertToWebCoupons(List<Coupon> coupList){
        List<WebCoupon> webCoupList = new ArrayList<>();
        if (coupList != null) {
            for (Coupon c : coupList) {
                webCoupList.add(new WebCoupon(c));
            }
        }
        return webCoupList;
    }
    
    public Coupon convertToCoupon() {
    	Coupon coup = new Coupon();
    	if(type != null &&
    			startDate != null &&
    				endDate != null) {
	        coup = new Coupon(
				getID(),
        		getTitle(),
        		convertToDate(getStartDate()),
                convertToDate(getEndDate()),
                getAmount(),
                CouponType.valueOf(getType().toUpperCase()),
                getMessage(),
                getPrice(),
                getImage()
            );
    	} else {
    		coup.setID(ID);
    	}

        return coup;
    }
    
    public static List<Coupon> convertToCoupons(List<WebCoupon> webCoupList){
        List<Coupon> coupList = new ArrayList<>();
        if (webCoupList != null) {
            for (WebCoupon wc : webCoupList) {
                coupList.add(wc.convertToCoupon());
            } 
        }
        return coupList;
    }

    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    
    private Date convertToDate(String dateStr) {
		LocalDate date = LocalDate.parse(dateStr);
		return Date.valueOf(date);
    }
    
    @Override
    public String toString() {
        return "Coupon [ID="        + ID 
                   + ", title="     + title 
                   + ", startDate=" + startDate 
                   + ", endDate="   + endDate
                   + ", amount="    + amount 
                   + ", type="      + type 
                   + ", message="   + message 
                   + ", price="     + price 
                   + ", image="     + image + "]";
    }

}

