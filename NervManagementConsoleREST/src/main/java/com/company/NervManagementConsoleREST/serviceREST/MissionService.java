package com.company.NervManagementConsoleREST.serviceREST;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.MissionDao;
import com.company.NervManagementConsoleREST.model.Mission;

@Path("/missions")
public class MissionService {
	private MissionDao missionDao = new MissionDao();
	
	@GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Mission> getMissions() {
    	try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
    		List<Mission> listMissions = missionDao.retrieve(entityManagerHandler);
            return listMissions;
    	}   
    }
	
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	 public void addMission(Mission m) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionDao.create(m, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Mission updateMission(Mission m) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionDao.updateMission(m, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
		return m;
	}
	
}
