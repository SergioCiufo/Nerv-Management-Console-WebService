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
	
	public List<MissionArchive> retriveByUserIdAndIdMission(User user, Mission mission) {
		return missionArchiveServiceDao.retriveByUserIdAndIdMission(user, mission);
	}
	
	public void addMissionArchive(MissionArchive missionArchive) {
		missionArchiveServiceDao.addMissionArchive(missionArchive);
	}
	
	public MissionArchive retriveByUserIdAndIdMissionResultProgress (User user, Mission mission) {
		return missionArchiveServiceDao.retriveByUserIdAndIdMissionResultProgress(user, mission);
	}
	
	public void updateMissionResult (MissionArchive missionArchive, MissionResult missionResult) {
		missionArchiveServiceDao.updateMissionResult(missionArchive, missionResult);
	}
	
	public List<MissionArchive> retriveByUserId (int userId) {
		return missionArchiveServiceDao.retriveByUserId(userId);
	}
}
