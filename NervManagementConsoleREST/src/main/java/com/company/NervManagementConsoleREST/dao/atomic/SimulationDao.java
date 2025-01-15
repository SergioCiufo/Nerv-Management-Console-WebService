package com.company.NervManagementConsoleREST.dao.atomic;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.User;

public class SimulationDao implements DaoInterface<Simulation> {
	private static final Logger logger = LoggerFactory.getLogger(SimulationDao.class);
	
	public SimulationDao() {
		super();
	}

	public List<Simulation> retrieve(EntityManagerHandler entityManagerHandler) {
		 try {
			 return entityManagerHandler.getEntityManager()
					    .createQuery("FROM Simulation", Simulation.class)
					    .getResultList();
		} catch (Exception e) {
			logger.error("Unexpected error retrive simulation");
			throw new DatabaseException("Unexpected error retrive simulation", e);
		}
	}

	public Simulation retrieveBySimulationId(int simulationId, EntityManagerHandler entityManagerHandler) {
		try {
			return entityManagerHandler.getEntityManager()
	    		.createQuery("FROM Simulation s WHERE s.simulationId = :simulationId", Simulation.class)
	    		.setParameter("simulationId", simulationId)
	    		.getSingleResult();
		}catch (NoResultException e) {
	        logger.error("No Simulation found with id: " + simulationId);
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving Simulation: " + simulationId + " " + e.getMessage());
	        throw new DatabaseException("Unexpected error retrive simulation by simulationId: "+simulationId, e);
	    }
	    
	}

	public List<Simulation> getSimulationAndParticipantsByUserId(User user, EntityManagerHandler entityManagerHandler) {
	    try {
	    	List<Simulation> simulations = entityManagerHandler.getEntityManager()
	        		.createQuery("FROM Simulation s " +
	                        "JOIN FETCH s.simulationParticipants sp " +
	                        "WHERE sp.user.id = :userId", Simulation.class)
	        		.setParameter("userId", user.getIdUser())
	        		.getResultList();
			
		    return simulations;
	    }catch (NoResultException e) {
	        logger.error("No Simulation found with userId: " + user.getIdUser());
	        return null;
	    } catch (HibernateException e) {
	        logger.error("Error retrieving Simulation by userId: " + user.getIdUser() + " " + e.getMessage());
	        throw new DatabaseException("Unexpected error retrive simulation by userId: "+user.getIdUser(), e);
	    }
	}

}
