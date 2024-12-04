package com.company.NervManagementConsoleREST.serviceREST;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.service.MissionArchiveService;

@Path("missionArchive")
public class MissionArchiveServiceRest {
	private MissionArchiveService missionArchiveService = new MissionArchiveService();
	
	@GET
	@Path("/{userId}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getArchiveMissions(@PathParam("userId") int userId) throws SQLException {
			List<MissionArchive> listMa = missionArchiveService.retriveByUserId(userId);
			if(listMa == null || listMa.isEmpty()) {
				return Response.status(Response.Status.NO_CONTENT).build(); //no content 204
			}
			return Response.ok(listMa).build();
	}
}
