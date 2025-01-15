package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.MissionParticipantsDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.User;

public class MissionParticipantsServiceDao {
	private MissionParticipantsDao missionParticipantsDao;
	private JpaUtil jpaUtil;
	
	public MissionParticipantsServiceDao() {
		this.missionParticipantsDao = new MissionParticipantsDao();
		this.jpaUtil = new JpaUtil();
	}
	
	public List<MissionParticipants> getMissionParticipantsByUserIdAndMissionId (User user, Mission mission){
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			return missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, mission, entityManagerHandler);
		}
	}
	
	public void startMission(MissionParticipants missionParticipants) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionParticipantsDao.startMission(missionParticipants, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public void removeParticipant(User user, Mission mission){
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionParticipantsDao.removeParticipant(user, mission, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public List<MissionParticipants> getActiveMissionsByUserId (int userId) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			return missionParticipantsDao.getActiveMissionsByUserId(userId, entityManagerHandler);
		}
	}
	
}
