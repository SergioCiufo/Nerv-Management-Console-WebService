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
import com.company.NervManagementConsoleREST.dao.MissionParticipantsDao;
import com.company.NervManagementConsoleREST.model.MissionParticipants;

@Path("missionParticipants")
public class MissionParticipantsService {
	private MissionParticipantsDao mpDao = new MissionParticipantsDao();
	
	
	@GET
	@Path("/{userId}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getActiveMissions(@PathParam("userId") int userId) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			List<MissionParticipants> listMp = mpDao.getActiveMissionsByUserId(userId, entityManagerHandler);
			if(listMp == null || listMp.isEmpty()) {
				return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
			}
			return Response.ok(listMp).build();
		}
	}
}
