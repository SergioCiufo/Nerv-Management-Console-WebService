package com.company.NervManagementConsoleREST.serviceREST;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.UserMemberStatsDao;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

@Path("/userMembers")
public class UserMembersStatsService {
	private UserMemberStatsDao umsDao = new UserMemberStatsDao();

	@GET
	@Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserMembers(@PathParam("userId") int userId) throws SQLException { //tipo response per personalizzarci la risposta di errore 204 ex
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		List<UserMembersStats> listUsm = umsDao.retrieveByUserId(userId, entityManagerHandler);
            if(listUsm == null || listUsm.isEmpty()) {
            	 return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
            }
            return Response.ok(listUsm).build();
    	}   
    }

}
