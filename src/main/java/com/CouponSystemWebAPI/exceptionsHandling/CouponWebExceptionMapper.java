package com.CouponSystemWebAPI.exceptionsHandling;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CouponWebExceptionMapper implements ExceptionMapper<Exception>{

	@Override
	public Response toResponse(Exception exception) {
		int errorCode = 0;
		String exceptionMessage = exception.getMessage();
		
		switch (exception.getClass().toString()) {
		case "LoginException":
			errorCode = 400;
			break;
		case "default":
			errorCode = 500;
			break;
		}
		
		ErrorMessage errorMessage = new ErrorMessage(errorCode, exceptionMessage);
		return Response.status(errorCode)
				.entity(errorMessage)
				.build();
	}
}
