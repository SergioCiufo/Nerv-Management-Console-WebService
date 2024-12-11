package com.company.NervManagementConsoleREST.dao.atomic;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Mission;

public class MissionDao implements DaoInterface<Mission> {
	private static final Logger logger = LoggerFactory.getLogger(MissionDao.class);
	public MissionDao() {
		super();
	}
	
	public void create(Mission ref, EntityManagerHandler entityManagerHandler) {
		try {
			entityManagerHandler.persist(ref);
		} catch (HibernateException e) {
			logger.error("Error adding mission: " + ref + e.getMessage());
			throw new DatabaseException("Unexpected error create mission" + ref.getMissionId(), e);
		}
	}
	
	public List<Mission> retrieve(EntityManagerHandler entityManagerHandler) {
		try {
			return entityManagerHandler.getEntityManager()
	    			.createQuery("FROM Mission ORDER BY missionId ASC", Mission.class)
	    			.getResultList();
		} catch (HibernateException e) {
			logger.error("Error retrive mission " + e.getMessage());
			throw new DatabaseException("Error while getting mission", e);
		}
	    
	}
	
	public Mission getMissionById(int idMission, EntityManagerHandler entityManagerHandler) {
		try {
			return entityManagerHandler.getEntityManager()
					.createQuery("FROM Mission m WHERE m.missionId = :missionId", Mission.class)
					.setParameter("missionId", idMission)
					.getSingleResult();

		}catch (NoResultException e) {
			logger.error("No mission found with id: " + idMission);
			return null;
		} catch (HibernateException e) {
			logger.error("Error retrieving mission with id: " + idMission, e);
			throw new DatabaseException("Error while getting mission, missionId: "+idMission, e);

		}
	}
	
	public void updateMission(Mission mission, EntityManagerHandler entityManagerHandler) {
		try {
			entityManagerHandler.getEntityManager()
		    .createQuery("UPDATE Mission m " +
		                 "SET m.name = :name, " +
		                 "m.description = :description, " +
		                 "m.exp = :exp, " +
		                 "m.level = :levelMin, " +
		                 "m.synchronizationRate = :synchronizationRate, " +
		                 "m.tacticalAbility = :tacticalAbility, " +
		                 "m.supportAbility = :supportAbility, " +
		                 "m.durationTime = :durationTime, " +
		                 "m.participantsMax = :participantsMax, " +
		                 "m.image = :image " +
		                 "WHERE m.missionId = :missionId")
		    .setParameter("name", mission.getName())
		    .setParameter("description", mission.getDescription())
		    .setParameter("exp", mission.getExp())
		    .setParameter("levelMin", mission.getLevel())
		    .setParameter("synchronizationRate", mission.getSynchronizationRate())
		    .setParameter("tacticalAbility", mission.getTacticalAbility())
		    .setParameter("supportAbility", mission.getSupportAbility())
		    .setParameter("durationTime", mission.getDurationTime())
		    .setParameter("participantsMax", mission.getParticipantsMax())
		    .setParameter("image", mission.getImage())
		    .setParameter("missionId", mission.getMissionId())
		    .executeUpdate();
		} catch (HibernateException e) {
	        logger.error("Error updating mission by missionId: " + mission.getMissionId() + e.getMessage());
	        throw new DatabaseException("Error while updating mission, missionId" + mission.getMissionId(), e);
		}
	}
	
}
