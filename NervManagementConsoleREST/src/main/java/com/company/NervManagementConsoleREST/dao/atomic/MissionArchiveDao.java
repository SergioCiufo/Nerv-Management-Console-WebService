package com.company.NervManagementConsoleREST.dao.atomic;

import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.MissionArchive.MissionResult;

public class MissionArchiveDao implements DaoInterface<MissionArchiveDao> {
	private static final Logger logger = LoggerFactory.getLogger(MissionArchiveDao.class);
	
	public MissionArchiveDao() {
		super();
	}

	public void addMissionArchive(MissionArchive ref, EntityManagerHandler entityManagerHandler) {
		try {
			entityManagerHandler.persist(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding MissionArchive: " + ref + e.getMessage());
	        throw new DatabaseException("Unexpected error create MissionArchive" + ref.getMissionArchiveId(), e);
	    }
	}
	
	public List<MissionArchive> retriveByUserIdAndIdMission(User user, Mission mission, EntityManagerHandler entityManagerHandler) {	   
	    try {
			return entityManagerHandler.getEntityManager()
    			.createQuery("FROM MissionArchive ma "
    					+ "WHERE ma.user.id = :userId AND ma.mission.missionId = :missionId", MissionArchive.class)
    			.setParameter("userId", user.getIdUser())
    			.setParameter("missionId", mission.getMissionId())
    			.getResultList();
		} catch (HibernateException e) {
			 logger.error("Error retrive MissionArchive, userId: " + user.getIdUser()+ " and missionId: " + mission.getMissionId() + e.getMessage());
		     throw new DatabaseException("Error retrive MissionArchive, userId: " + user.getIdUser()+ " and missionId: " + mission.getMissionId(), e);
		}
	}
	
	public List<MissionArchive> retriveByUserId(int userId, EntityManagerHandler entityManagerHandler) {
	    try {
			return entityManagerHandler.getEntityManager()
    			.createQuery("FROM MissionArchive ma "
    					+ "WHERE ma.user.id = :userId", MissionArchive.class)
    			.setParameter("userId", userId)
    			.getResultList();
		} catch (HibernateException e) {
			 logger.error("Error retrive MissionArchive, userId: " + userId + e.getMessage());
		     throw new DatabaseException("Error retrive MissionArchive, userId: " + userId , e);
		}
	}

	public MissionArchive retriveByUserIdAndIdMissionResultProgress(User user, Mission mission, EntityManagerHandler entityManagerHandler) {
		try {
	        return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM MissionArchive "
	    					+ "WHERE user.idUser = :userId AND mission.missionId = :missionId AND result = 'PROGRESS'", MissionArchive.class)
	    			.setParameter("userId", user.getIdUser())
	    			.setParameter("missionId", mission.getMissionId())
	    			.getResultList()
	    			.stream()
	    	        .findFirst()
	    	        .orElse(null);  //null se la lista è vuota

	    } catch (HibernateException e) {
	        logger.error("Error retrieving MissionArchive in Progress for userId: " + user.getIdUser() + " and missionId: " + mission.getMissionId() + " ", e.getMessage());
	        throw new DatabaseException("Error retrieving MissionArchive in Progress for userId: " + user.getIdUser() + " and missionId: " + mission.getMissionId() + " ", e);
	    }
	}

	public void updateMissionResult(MissionArchive ref, MissionResult result, EntityManagerHandler entityManagerHandler) {
	    try {
	        entityManagerHandler.getEntityManager()
	        .createQuery("UPDATE MissionArchive ma SET ma.result = :result "
	        		+ "WHERE ma.missionCode = :missionCode")
	        .setParameter("result", result)
	        .setParameter("missionCode", ref.getMissionCode())
	        .executeUpdate();
	    } catch (HibernateException e) {
	        e.printStackTrace();
	    }
	}
}
