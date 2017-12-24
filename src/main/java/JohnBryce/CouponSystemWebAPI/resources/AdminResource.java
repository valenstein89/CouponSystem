package JohnBryce.CouponSystemWebAPI.resources;

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

import com.projectStage1.exceptions.CompanyCreationException;
import com.projectStage1.exceptions.CompanyNameExistException;
import com.projectStage1.exceptions.CompanyNotFoundException;
import com.projectStage1.exceptions.CompanyRemovalException;
import com.projectStage1.exceptions.CompanyUpdateException;
import com.projectStage1.exceptions.CustomerCreationException;
import com.projectStage1.exceptions.CustomerNameExistException;
import com.projectStage1.exceptions.CustomerNotFoundException;
import com.projectStage1.exceptions.CustomerRemovalException;
import com.projectStage1.exceptions.CustomerUpdateException;
import com.projectStage1.exceptions.GetAllCompaniesException;
import com.projectStage1.exceptions.GetAllCouponException;
import com.projectStage1.exceptions.GetAllCustomersException;
import com.projectStage1.exceptions.GetCustomerException;
import com.projectStage1.exceptions.RemoveCouponsOfCompanyException;
import com.projectStage1.exceptions.RollbackDataBaseException;
import com.projectStage1.facades.AdminFacade;

import JohnBryce.CouponSystemWebAPI.webBeans.WebCompany;
import JohnBryce.CouponSystemWebAPI.webBeans.WebCustomer;

@Path("admin")
public class AdminResource 
{
	@Context private HttpServletRequest request;
	private AdminFacade facade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/customers")
    public Response getAllCustomers() 
    {
        List<WebCustomer> customersList = null;
        GenericEntity<List<WebCustomer>> entity = null;
        try {
            if (getFacade() != null) {
                customersList = WebCustomer.convertToWebCustomers(facade.getAllCustomers());
                entity = new GenericEntity<List<WebCustomer>>(customersList) {};
            }
        } catch (GetAllCustomersException e) {
            return Response.ok(e.getMessage()).status(200).build();
        }
        return Response.ok(entity).status(200).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/customers/{ID}")
    public Response getCustomer(@PathParam("ID") long ID) {
        WebCustomer result = null;
        try {
            if (getFacade() != null) {
                result = new WebCustomer(facade.getCustomer(ID));
            }
        } catch (CustomerNotFoundException | GetCustomerException e) {
        	return Response.ok(e.getMessage()).status(200).build();
        }
        return Response.ok(result).status(200).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/companies")
    public Response getAllCompanies()
    {
        List<WebCompany> result = new ArrayList<>();
        GenericEntity<List<WebCompany>> entity = null;
        try {
            if (getFacade() != null) {
                result = WebCompany.convertToWebCompanies(facade.getAllCompanies());
                entity = new GenericEntity<List<WebCompany>>(result) {};
            }
        } catch (GetAllCompaniesException e) {
        	return Response.ok(e.getMessage()).status(200).build();
        }
        return Response.ok(entity).status(200).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/companies/{ID}")
    public Response getCompany(@PathParam("ID") long ID)
    {
        WebCompany result = null;
        try {
            if (getFacade() != null) {
                result = new WebCompany(facade.getCompany(ID));
            }
        } catch (CompanyNotFoundException e) {
        	return Response.ok(e.getMessage()).status(200).build();
        }
        return Response.ok(result).status(200).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/companies")
    public Response createCompany(WebCompany wc)
    {
        if (getFacade() != null) {
            try {
                facade.createCompany(wc.convertToCompany());
            } catch (CompanyCreationException | GetAllCompaniesException | GetAllCouponException
                    | CompanyNameExistException | RollbackDataBaseException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Company created sucessfully!").status(200).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/customers")
    public Response createCustomer(WebCustomer wc)
    {
        if (getFacade() != null) {
            try {
                facade.createCustomer(wc.convertToCustomer());
            } catch (CustomerNameExistException | CustomerCreationException | GetAllCustomersException
                    | RollbackDataBaseException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Customer created successfully!").status(200).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/companies")
    public Response updateCompany(WebCompany wc)
    {
        if (getFacade() != null) {
            try {
                facade.updateCompany(wc.convertToCompany());
            } catch (CompanyUpdateException | CompanyNotFoundException | RollbackDataBaseException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Company updated successfully!").status(200).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/customers")
    public Response updateCustomer(WebCustomer wc)
    {
        if (getFacade() != null) {
            try {
                facade.updateCustomer(wc.convertToCustomer());
            } catch (CustomerNameExistException | GetAllCustomersException | CustomerUpdateException
                    | RollbackDataBaseException | GetCustomerException | CustomerNotFoundException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Customer updated successfully!").status(200).build();
    }
    
    @DELETE
    @Path("/companies/{ID}")
    public Response removeCompany(@PathParam("ID") long ID)
    {
        WebCompany wc = new WebCompany(ID);
        if (getFacade() != null) {
            try {
                facade.removeCompany(wc.convertToCompany());
            } catch (CompanyRemovalException | RemoveCouponsOfCompanyException | RollbackDataBaseException
                    | CompanyNotFoundException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Company deleted successfully!").status(200).build();
    }
    
    @DELETE
    @Path("/customers/{ID}")
    public Response removeCustomer(@PathParam("ID") long ID)
    {
        WebCustomer wc = new WebCustomer(ID);
        if (getFacade() != null) {
            try {
                facade.removeCustomer(wc.convertToCustomer());
            } catch (CustomerRemovalException | RollbackDataBaseException | GetCustomerException
                    | CustomerNotFoundException e) {
                return Response.ok(e.getMessage()).status(200).build();
            }
        }
        return Response.ok("Customer deleted successfully!").status(200).build();
    }

    private AdminFacade getFacade() {
        if (facade == null) {
//                facade = (AdminFacade) CouponSystem.getInstance().login("admin", "1234", ClientType.ADMIN);
            	facade = (AdminFacade) request.getSession().getAttribute("facade");
        }
        return facade;
    }
    
}