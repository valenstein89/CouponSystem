package com.projectStage1.beans;

import java.sql.Date;

import com.projectStage1.util.IDGenerator;

public class Coupon {

    private long ID;
    private String title;
    private Date startDate;
    private Date endDate;
    private int amount;
    private CouponType type;
    private String message;
    private double price;
    private String image;

    public Coupon()
    {
//        //Creates a new random ID number
//        ID = IDGenerator.makeNewID();
    }

    /**
     * This Constructor creates a brand new Coupon
     * with new unique ID number.
     */
    public Coupon(String title, Date startDate, Date endDate,
            int amount, CouponType type, String message,
            double price, String image)
    {
//        // Uses default constructor
//        // to obtain new unique ID
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
    public Coupon(long ID, String title, Date startDate,
            Date endDate, int amount, CouponType type,
            String message, double price, String image)
    {
        this(title, startDate, endDate, amount, type, message, price, image);

        setID(ID); //Uses an existing ID number from DB or other source.
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
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public CouponType getType() {
        return type;
    }
    public void setType(CouponType type) {
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
