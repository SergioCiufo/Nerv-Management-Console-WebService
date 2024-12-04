package com.company.NervManagementConsoleREST.serviceREST;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.service.UserMemberStatsService;

@Path("/userMembers")
public class UserMembersStatsServiceRest {
	private UserMemberStatsService userMemberStatsService= new UserMemberStatsService();

	@GET
	@Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserMembers(@PathParam("userId") int userId) throws SQLException { //tipo response per personalizzarci la risposta di errore 204 ex
    		List<UserMembersStats> listUsm = userMemberStatsService.retrieveByUserId(userId);
            if(listUsm == null || listUsm.isEmpty()) {
            	 return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
            }
            return Response.ok(listUsm).build();
    }

}
