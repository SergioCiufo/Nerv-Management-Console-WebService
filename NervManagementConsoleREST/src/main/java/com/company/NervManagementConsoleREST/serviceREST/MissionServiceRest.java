package com.company.NervManagementConsoleREST.serviceREST;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.service.MissionService;

@Path("/missions")
public class MissionServiceRest {
	private MissionService missionService = new MissionService();

	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Mission> getMissions() throws SQLException {
		return missionService.retrieveMissions();
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response addMission(Mission mission) throws SQLException {
		try {
			missionService.addMission(mission);
			return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build(); //bad request ma andrebbe ampliato l'errore //tipo missioone già esistente con questo username
		}
	}

	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateMission(Mission mission) throws SQLException {
		try {
			missionService.updateMission(mission);
			return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build(); //bad request ma andrebbe ampliato l'errore //tipo missioone già esistente con questo username
		}

	}

}
