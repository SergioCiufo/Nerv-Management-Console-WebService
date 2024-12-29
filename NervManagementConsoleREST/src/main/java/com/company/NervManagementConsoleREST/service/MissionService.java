package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.dao.service.MissionServiceDao;
import com.company.NervManagementConsoleREST.model.Mission;

public class MissionService {
	private MissionServiceDao missionServiceDao;
	
	public MissionService() {
		this.missionServiceDao = new MissionServiceDao();
	}
	
	public List<Mission> retrieveMissions(){
		return missionServiceDao.retrieveMissions();
	}
	
	public Mission getMissionById(int idMission) {
		return missionServiceDao.getMissionById(idMission);
	}
	
	public void addMission(Mission mission) {
		missionServiceDao.addMission(mission);
	}
	
	public void updateMission(Mission mission) {
		missionServiceDao.updateMission(mission);
	}
	
	public void addEventMission(Mission mission) {
		missionServiceDao.addEventMission(mission);
	}

}
