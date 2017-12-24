package JohnBryce.CouponSystemWebAPI.resources;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.projectStage1.beans.CouponType;
import com.projectStage1.exceptions.CompanyNotFoundException;
import com.projectStage1.exceptions.CouponCreationException;
import com.projectStage1.exceptions.CouponNameExistException;
import com.projectStage1.exceptions.CouponNotAvailableException;
import com.projectStage1.exceptions.CouponRemovalException;
import com.projectStage1.exceptions.CouponUpdateException;
import com.projectStage1.exceptions.GetAllCouponException;
import com.projectStage1.exceptions.GetCouponsByCompanyException;
import com.projectStage1.exceptions.GetCouponsOfCompanyByTypeException;
import com.projectStage1.exceptions.GetCouponsOfCompanyMaxDateException;
import com.projectStage1.exceptions.GetCouponsOfCompanyMaxPriceException;
import com.projectStage1.exceptions.RollbackDataBaseException;
import com.projectStage1.facades.CompanyFacade;

import JohnBryce.CouponSystemWebAPI.webBeans.WebCoupon;

@Path("company")
public class CompanyResource 
{
	@Context private HttpServletRequest request;
    private CompanyFacade facade;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/coupon")
    public Response getAllCoupons()
    {
        List<WebCoupon> result = null;
        GenericEntity<List<WebCoupon>> entity = null;
        if (getFacade() != null) {
            try {
                result = WebCoupon.convertToWebCoupons(facade.getAllCoupons());
                entity = new GenericEntity<List<WebCoupon>>(result) {};
            } catch (CompanyNotFoundException | GetCouponsByCompanyException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok(entity).status(200).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/coupon/{ID}")
    public Response getCoupon(@PathParam("ID") long ID)
    {
        WebCoupon result = null;
        if (getFacade() != null) {
            try {
                result = new WebCoupon(facade.getCoupon(ID));
            } catch (CouponNotAvailableException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok(result).status(200).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/coupon/type/{couponType}")
    public Response getCouponsByType(@PathParam("couponType") String couponType)
    {    
        List<WebCoupon> result = new ArrayList<>();
        GenericEntity<List<WebCoupon>> entity = null;
        if (getFacade() != null) {
            CouponType ct = CouponType.valueOf(couponType.toUpperCase());
            try {
                result = WebCoupon.convertToWebCoupons(facade.getCouponsByType(ct));
                entity = new GenericEntity<List<WebCoupon>>(result) {};
            } catch (GetCouponsOfCompanyByTypeException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok(entity).status(200).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/coupon/price/{price}")
    public Response getCouponsMaxPrice(@PathParam("price") double price)
    {
        List<WebCoupon> result = new ArrayList<>();
        GenericEntity<List<WebCoupon>> entity = null;
        if (getFacade() != null) {
            try {
            	result = WebCoupon.convertToWebCoupons(facade.getCouponsMaxPrice(price));
            	entity = new GenericEntity<List<WebCoupon>>(result) {};
            } catch (GetCouponsOfCompanyMaxPriceException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok(entity).status(200).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/coupon/date/{date}")
    public Response getCouponsMaxDate(@PathParam("date") String dateStr)
    {
        List<WebCoupon> result = new ArrayList<>();
        GenericEntity<List<WebCoupon>> entity = null;
        if (getFacade() != null) {
            try {
                // Convert param-string-date to SQL Date
                LocalDate javaDate = LocalDate.parse(dateStr);
                Date sqlDate = Date.valueOf(javaDate);
                // Get them!
                result = WebCoupon.convertToWebCoupons(facade.getCouponsMaxDate(sqlDate));
                entity = new GenericEntity<List<WebCoupon>>(result) {};
            } catch (GetCouponsOfCompanyMaxDateException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok(entity).status(200).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/coupon")
    public Response createCoupon(WebCoupon wc)
    {
        if(getFacade() != null) {
            try {
                facade.createCoupon(wc.convertToCoupon());
            } catch (CouponCreationException | GetAllCouponException | CouponNameExistException
                    | RollbackDataBaseException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Coupon created successfully!").status(200).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/coupon")
    public Response updateCoupon(WebCoupon wc)
    {
        if (getFacade() != null) {
            try {
                facade.updateCoupon(wc.convertToCoupon());
            } catch (CouponUpdateException | GetAllCouponException | CouponNameExistException
                    | RollbackDataBaseException | CouponNotAvailableException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Coupon updated successfully!").status(200).build();
    }

    @DELETE
    @Path("/coupon/{id}")
    public Response removeCoupon(@PathParam("id") long id)
    {
    	System.out.println(id);
    	WebCoupon wc = new WebCoupon(id);
        if (getFacade() != null) {
            try {
                facade.removeCoupon(wc.convertToCoupon());
            } catch (CouponRemovalException | RollbackDataBaseException | CouponNotAvailableException e) {
            	return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Coupon removed successfully!").status(200).build();
    }

    private CompanyFacade getFacade() {
        if (facade == null){
//                facade = (CompanyFacade) CouponSystem.getInstance().login("TestCompany", "1234", ClientType.COMPANY);
        	facade = (CompanyFacade)request.getSession().getAttribute("facade");
        }
        return facade;
    }
}
