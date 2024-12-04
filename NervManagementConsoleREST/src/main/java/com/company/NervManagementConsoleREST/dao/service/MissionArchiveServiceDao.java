package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.MissionArchiveDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionArchive.MissionResult;
import com.company.NervManagementConsoleREST.model.User;

public class MissionArchiveServiceDao {
	private MissionArchiveDao missionArchiveDao;
	
	public MissionArchiveServiceDao() {
		this.missionArchiveDao = new MissionArchiveDao();
	}
	
	public List<MissionArchive> retriveByUserIdAndIdMission(User user, Mission mission) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return missionArchiveDao.retriveByUserIdAndIdMission(user, mission, entityManagerHandler);
		}
	}
	
	public void addMissionArchive (MissionArchive missionArchive) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionArchiveDao.addMissionArchive(missionArchive, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public MissionArchive retriveByUserIdAndIdMissionResultProgress (User user, Mission mission) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return missionArchiveDao.retriveByUserIdAndIdMissionResultProgress(user, mission, entityManagerHandler);	
		}
	}
	
	public void updateMissionResult(MissionArchive missionArchive, MissionResult missionResult) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionArchiveDao.updateMissionResult(missionArchive, missionResult, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public List<MissionArchive> retriveByUserId (int userId) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return missionArchiveDao.retriveByUserId(userId, entityManagerHandler);
		}
	}
}
