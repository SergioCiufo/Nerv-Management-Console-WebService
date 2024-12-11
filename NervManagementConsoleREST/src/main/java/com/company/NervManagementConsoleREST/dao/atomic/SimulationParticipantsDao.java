package com.company.NervManagementConsoleREST.dao.atomic;


import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;


public class SimulationParticipantsDao implements DaoInterface<SimulationParticipant> {
	private static final Logger logger = LoggerFactory.getLogger(SimulationParticipantsDao.class);
	
	public SimulationParticipantsDao() {
		super();
	}

	public void createParticipant(SimulationParticipant ref, EntityManagerHandler entityManagerHandler) {
		try {
			entityManagerHandler.persist(ref);
	    } catch (HibernateException e) {
	        logger.error("Error adding simulationPartecipant: " + ref + e.getMessage());
	        throw new DatabaseException("Unexpected error create simulationPartecipant id: " + ref.getSimulationParticipantId(), e);
	    }
	}

	public SimulationParticipant getParticipantbyUserAndMemberId(User user, Member member, EntityManagerHandler entityManagerHandler) {
		try {
			 return entityManagerHandler.getEntityManager()
					.createQuery("FROM SimulationParticipant sp "
							+ "WHERE sp.user.id = :userId AND sp.member.id = :memberId", SimulationParticipant.class)
					.setParameter("userId", user.getIdUser())
					.setParameter("memberId", member.getIdMember())
					.getSingleResult();
		
		}catch (NoResultException e) {
	        logger.error("No participant found with id: " + user.getIdUser() + " member " + member.getIdMember() );
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving participant for userId: " + user.getIdUser() + " memberid: " + member.getIdMember() + " " + e.getMessage());
	        throw new DatabaseException("Unexpected error retrive simulationPartecipant by userid: " + user.getIdUser(), e);
	    }
	}

	public void removeParticipant(User user, Simulation simulation, EntityManagerHandler entityManagerHandler) {
	    try {
	        entityManagerHandler.getEntityManager()
	        .createQuery("DELETE FROM SimulationParticipant sp " +
	                     "WHERE sp.user.id = :userId AND sp.simulation.id = :simulationId")
	        .setParameter("userId", user.getIdUser())
	        .setParameter("simulationId", simulation.getSimulationId())
	        .executeUpdate();
	    } catch (HibernateException e) {
	        logger.error("Error removing participant for userId: " + user.getIdUser() + " simulationId: " + simulation.getSimulationId(), e);
	        throw new DatabaseException("Unexpected error removing participant userid: " + user.getIdUser() + "simId: "+ simulation.getSimulationId() , e);
	    }
	}
	
}
