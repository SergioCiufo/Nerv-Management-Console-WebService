package com.company.NervManagementConsoleREST.serviceSOAP;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.core.Response;

import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.RegisterService;
import com.company.NervManagementConsoleREST.service.UserService;

@WebService
public class UserServiceSoap {
	private UserService userService = new UserService();
	private RegisterService registerService = new RegisterService();
	
	@WebMethod(operationName = "usersList")
	public List<User> usersList() throws SQLException {
		return userService.getUsersList();
	}
	
	@WebMethod(operationName = "addUser")
	public Response addUser(@WebParam(name = "user") User u) {
		try {
			registerService.register(u);
			return Response.status(Response.Status.CREATED).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@WebMethod(operationName = "updateUser")
	public Response updateUser(@WebParam(name = "user") User u) {
		try {
			userService.updateUser(u);
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@WebMethod(operationName = "deleteUser")
	public Response deleteUser(@WebParam(name = "userId") int userId) {
		try {
			userService.removeUser(userId);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
}
