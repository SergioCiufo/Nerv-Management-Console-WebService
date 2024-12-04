package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.dao.service.MissionArchiveServiceDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionArchive.MissionResult;
import com.company.NervManagementConsoleREST.model.User;

public class MissionArchiveService {
	private MissionArchiveServiceDao missionArchiveServiceDao;
	
	public MissionArchiveService() {
		this.missionArchiveServiceDao = new MissionArchiveServiceDao();
	}
	
	public List<MissionArchive> retriveByUserIdAndIdMission(User user, Mission mission) throws SQLException{
		return missionArchiveServiceDao.retriveByUserIdAndIdMission(user, mission);
	}
	
	public void addMissionArchive(MissionArchive missionArchive) throws SQLException{
		missionArchiveServiceDao.addMissionArchive(missionArchive);
	}
	
	public MissionArchive retriveByUserIdAndIdMissionResultProgress (User user, Mission mission) throws SQLException{
		return missionArchiveServiceDao.retriveByUserIdAndIdMissionResultProgress(user, mission);
	}
	
	public void updateMissionResult (MissionArchive missionArchive, MissionResult missionResult) throws SQLException{
		missionArchiveServiceDao.updateMissionResult(missionArchive, missionResult);
	}
	
	public List<MissionArchive> retriveByUserId (int userId) throws SQLException{
		return missionArchiveServiceDao.retriveByUserId(userId);
	}
}
