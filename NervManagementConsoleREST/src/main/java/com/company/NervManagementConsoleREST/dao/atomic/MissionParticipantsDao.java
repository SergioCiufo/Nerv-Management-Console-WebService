package com.company.NervManagementConsoleREST.dao.atomic;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.User;

public class MissionParticipantsDao implements DaoInterface<MissionParticipants> {	
	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	public MissionParticipantsDao() {
		super();
	}

	public void startMission(MissionParticipants ref, EntityManagerHandler entityManagerHandler) {
		try {
			entityManagerHandler.persist(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding missionParticipant: " + ref + e.getMessage());
	        throw new DatabaseException("Unexpected error create mission, idMission:" + ref.getMissionParticipantsId(), e);
	    }
	}
	
	public List<MissionParticipants> getMissionParticipantsByUserIdAndMissionId(User user, Mission mission, EntityManagerHandler entityManagerHandler) {
	    try {
			return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM MissionParticipants mp "
	    					+ "WHERE mp.user.id = :userId AND mp.mission.missionId = :missionId", MissionParticipants.class)
	    			.setParameter("userId", user.getIdUser())
	    			.setParameter("missionId", mission.getMissionId())
	    			.getResultList();
		} catch (HibernateException e) {
	        logger.error("Error retrive missionParticipant by userId: " + user.getIdUser() + "and missionId: "+ mission.getMissionId() + e.getMessage());
	        throw new DatabaseException("Error retrive missionParticipant by userId: " + user.getIdUser() + "and missionId: "+ mission.getMissionId() , e);
	    }

	}
	
	public List<MissionParticipants> getActiveMissionsByUserId(int userId, EntityManagerHandler entityManagerHandler) {
	    try {
			return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM MissionParticipants mp "
	    					+ "WHERE mp.user.id = :userId", MissionParticipants.class)
	    			.setParameter("userId", userId)
	    			.getResultList();	
		} catch (HibernateException e) {
	        logger.error("Error retrive missionParticipant by userId: " + userId + e.getMessage());
	        throw new DatabaseException("Error retrive missionParticipant by userId: " + userId, e);
	    }
		
	}
	
	public void removeParticipant(User user, Mission mission, EntityManagerHandler entityManagerHandler) {
	    try {
	        entityManagerHandler.getEntityManager()
	        .createQuery("DELETE FROM MissionParticipants mp " +
	                     "WHERE mp.user.id = :userId AND mp.mission.id = :missionId")
	        .setParameter("userId", user.getIdUser())
	        .setParameter("missionId", mission.getMissionId())
	        .executeUpdate();
	    } catch (HibernateException e) {
	        logger.error("Error removing participant for userId: " + user.getIdUser() + " missionId: " + mission.getMissionId(), e);
	        throw new DatabaseException("Error removing missionParticipant by userId: " + user.getIdUser() + " and missionId: " + mission.getMissionId(), e);
	    }
	}
	
}
