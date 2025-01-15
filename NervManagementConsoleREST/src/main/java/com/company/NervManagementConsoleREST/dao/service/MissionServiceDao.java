package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.MissionDao;
import com.company.NervManagementConsoleREST.model.Mission;

public class MissionServiceDao {
	private MissionDao missionDao;
	private JpaUtil jpaUtil;
	
	public MissionServiceDao() {
		this.missionDao = new MissionDao();
		this.jpaUtil = new JpaUtil();
	}
	
	public List<Mission> retrieveMissions(){
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			return missionDao.retrieve(entityManagerHandler);
		}
	}
	
	public Mission getMissionById(int idMission) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			return missionDao.getMissionById(idMission, entityManagerHandler);
		}
	}
	
	public void addMission(Mission mission) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionDao.create(mission, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public void updateMission (Mission mission) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionDao.updateMission(mission, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	//Ci garantiamo di avere un evento disponibile alla volta
	public void addEventMission(Mission mission) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			missionDao.updateEventMissionByAvailableTrue(entityManagerHandler);
			missionDao.create(mission, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
}
