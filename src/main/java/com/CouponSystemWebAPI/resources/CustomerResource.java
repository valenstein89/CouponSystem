package com.CouponSystemWebAPI.resources;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.CouponSystemCore.beans.CouponType;
import com.CouponSystemCore.exceptions.CouponPurchaseException;
import com.CouponSystemCore.exceptions.CustomerCreationException;
import com.CouponSystemCore.exceptions.GetCouponByCustomerException;
import com.CouponSystemCore.exceptions.GetCouponOfCustomerByPriceException;
import com.CouponSystemCore.exceptions.GetCouponOfCustomerByTypeException;
import com.CouponSystemCore.exceptions.GetCouponsOfCustomerException;
import com.CouponSystemCore.exceptions.PurchaseCouponException;
import com.CouponSystemCore.exceptions.RollbackDataBaseException;
import com.CouponSystemCore.facades.CustomerFacade;
import com.CouponSystemWebAPI.webBeans.WebCoupon;

@Path("customer")
public class CustomerResource {

	@Context private HttpServletRequest request;
    private CustomerFacade facade;
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/coupon")
    public Response getAllPurchasedCoupons(){
        List<WebCoupon> result = new ArrayList<>();
        GenericEntity<List<WebCoupon>> entity = null;
        if (getFacade() != null) {
            try {
                result = WebCoupon.convertToWebCoupons(facade.getAllPurchasedCoupons());
                entity = new GenericEntity<List<WebCoupon>>(result) {};
            } catch (GetCouponsOfCustomerException | GetCouponByCustomerException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok(entity).status(200).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/coupon/type/{couponType}")
    public Response getAllPurchasedCouponsByType(@PathParam("couponType") String couponType){
        List<WebCoupon> result = new ArrayList<>();
        GenericEntity<List<WebCoupon>> entity = null;
        if (getFacade() != null) {
            CouponType type = CouponType.valueOf(couponType.toUpperCase());
            try {
                result = WebCoupon.convertToWebCoupons(facade.getAllPurchasedCouponsByType(type));
                entity = new GenericEntity<List<WebCoupon>>(result) {};
            } catch (GetCouponOfCustomerByTypeException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok(entity).status(200).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/coupon/price/{price}")
    public Response getAllPurchasedCouponsByPrice(@PathParam("price") double price){
        List<WebCoupon> result = new ArrayList<>();
        GenericEntity<List<WebCoupon>> entity = null;
        if (getFacade() != null) {
            try {
                result = WebCoupon.convertToWebCoupons(facade.getAllPurchasedCouponsByPrice(price));
                entity = new GenericEntity<List<WebCoupon>>(result) {};
            } catch (GetCouponOfCustomerByPriceException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok(entity).status(200).build();
    }
    
    @GET
    @Path("/coupon/{id}")
    public Response purchaseCoupon(@PathParam("id") long id)
    {
        if (getFacade() != null) {
            try {
                facade.purchaseCoupon(id);
            } catch (CustomerCreationException | PurchaseCouponException | RollbackDataBaseException
                    | GetCouponsOfCustomerException | GetCouponByCustomerException | CouponPurchaseException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Coupon purchased successfully!").status(200).build();
    }
    
    private CustomerFacade getFacade() {
        if (facade == null) {
//                facade = (CustomerFacade) CouponSystem.getInstance().login("TestCustomer", "1234", ClientType.CUSTOMER);
            	facade = (CustomerFacade) request.getSession().getAttribute("facade");
        }
        return facade;
    }
    
}
