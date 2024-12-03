package com.company.NervManagementConsoleREST.serviceREST;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.UserDao;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.RegisterService;

@Path("/users")
public class UserService {
	private UserDao userDao = new UserDao();
	private RegisterService registerService = new RegisterService();

    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("userId") int userId) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
            User u = userDao.getUserById(userId, entityManagerHandler);
    		return u;
    	}
    }
    
    //metodo con QUERY PARAMS //crea conflitto con la get normale e quindi mettiamo questa
    //http://localhost:8080/NervManagementConsoleProva/users?name=Misato
    //http://localhost:8080/NervManagementConsoleProva/users?name=Misato&surname=Katsuragi
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getUsers(@QueryParam("name") String name,	
    							@QueryParam("surname") String surname) throws SQLException{
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		List<User> listUsers = userDao.retrieve(entityManagerHandler);
    		
    		//filtro QueryParam
    		if(name != null) {
    			listUsers = listUsers.stream()
    					.filter(user -> user.getName().equalsIgnoreCase(name))
    					 .collect(Collectors.toList());
    		}
    		if (surname != null) {
                listUsers = listUsers.stream()
                    .filter(user -> user.getSurname().equalsIgnoreCase(surname))
                    .collect(Collectors.toList());
            }
    		return listUsers;
    	}	   	
    }
    
    @GET
    @Path("/username/{username}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUserByUsername(@PathParam("username") String username) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
            User u = userDao.getUserByUsername(username, entityManagerHandler);
    		return u;
    	}
    }
    
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public void addUser(User u) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		registerService.register(u.getName(), u.getSurname(), u.getUsername(), u.getPassword());
    	}
    }
    
    @PUT
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void updateUser(User u) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		entityManagerHandler.beginTransaction();
    		userDao.updateUser(u, entityManagerHandler);
    		entityManagerHandler.commitTransaction();
    	}
    }
    
    @DELETE
    @Path("/{userId}/delete")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteUser(@PathParam("userId") int userId) throws SQLException {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		entityManagerHandler.beginTransaction();
    		userDao.removeUser(userId, entityManagerHandler);
    		entityManagerHandler.commitTransaction();
    	}
    }
    
    
    
}
