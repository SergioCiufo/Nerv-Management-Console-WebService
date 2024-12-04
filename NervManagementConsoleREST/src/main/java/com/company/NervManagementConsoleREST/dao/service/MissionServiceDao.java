package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.MissionDao;
import com.company.NervManagementConsoleREST.model.Mission;

public class MissionServiceDao {
	private MissionDao missionDao;
	
	public MissionServiceDao() {
		this.missionDao = new MissionDao();
	}
	
	public List<Mission> retrieveMissions() throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return missionDao.retrieve(entityManagerHandler);
		}
	}
	
	public Mission getMissionById(int idMission) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return missionDao.getMissionById(idMission, entityManagerHandler);
		}
	}
	
	public void addMission(Mission mission) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionDao.create(mission, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public void updateMission (Mission mission) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionDao.updateMission(mission, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
}
