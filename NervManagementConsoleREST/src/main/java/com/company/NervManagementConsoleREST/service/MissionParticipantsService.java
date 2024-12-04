package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.dao.service.MissionParticipantsServiceDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.User;

public class MissionParticipantsService {
	private MissionParticipantsServiceDao missionParticipantsServiceDao;
	
	public MissionParticipantsService() {
		this.missionParticipantsServiceDao = new MissionParticipantsServiceDao();
	}
	
	public List<MissionParticipants> getMissionParticipantsByUserIdAndMissionId (User user, Mission mission) throws SQLException{
		return missionParticipantsServiceDao.getMissionParticipantsByUserIdAndMissionId(user, mission);
	}
	
	public void startMission(MissionParticipants missionParticipants) throws SQLException{
		missionParticipantsServiceDao.startMission(missionParticipants);
	}
	
	public void removeParticipant(User user, Mission mission) throws SQLException{
		missionParticipantsServiceDao.removeParticipant(user, mission);
	}
	
	public List<MissionParticipants> getActiveMissionsByUserId (int userId) throws SQLException{
		return missionParticipantsServiceDao.getActiveMissionsByUserId(userId);
	}

}
