package com.company.NervManagementConsoleREST.serviceREST;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.UserService;

import com.company.NervManagementConsoleREST.service.RegisterService;

@Path("/users")
public class UserServiceRest {
	private UserService userService = new UserService();
	private RegisterService registerService = new RegisterService();

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("userId") int userId) throws SQLException {
    	return userService.getUserById(userId);
    }
    
    //metodo con QUERY PARAMS //crea conflitto con la get normale e quindi mettiamo questa
    //http://localhost:8080/NervManagementConsoleREST/users?name=Misato
    //http://localhost:8080/NervManagementConsoleREST/users?name=Misato&surname=Katsuragi
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUsers(@QueryParam("name") String name,	
    		@QueryParam("surname") String surname) throws SQLException{
    	try {

    		List<User>listaUtenti = userService.getUsersbyNameAndOrSurname(name, surname);   
    		//GenericArrayType<User> genericListUser = new GenericArrayType<List<User>>(listaUtenti, getClass());
    		GenericEntity<List<User>> userList = new GenericEntity<List<User>>(listaUtenti) {};
    		return Response.status(200).entity(userList).build();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    }

    @GET
    @Path("/username/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUserByUsername(@PathParam("username") String username) throws SQLException {
    	return userService.getUserByUsername(username);
    }
    
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addUser(User u) throws SQLException {
    	try {
    		registerService.register(u.getName(), u.getSurname(), u.getUsername(), u.getPassword());
    		return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		//internal servel error meglio
		}	
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateUser(User u) throws SQLException {
    	try {
    		userService.updateUser(u);
    		return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build(); //bad request ma andrebbe ampliato l'errore //tipo utente già esistente con questo username
		}	
    }
    
    @DELETE
    @Path("/{userId}/delete")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteUser(@PathParam("userId") int userId) throws SQLException {
    	try {
    		userService.removeUser(userId);
    		return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build(); //bad request ma andrebbe ampliato l'errore //tipo utente già esistente con questo username
		}	
    	
    }

}
