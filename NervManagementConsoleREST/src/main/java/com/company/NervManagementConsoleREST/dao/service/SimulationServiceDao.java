package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.SimulationDao;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.User;

public class SimulationServiceDao {
	private SimulationDao simulationDao;

	public SimulationServiceDao() {
		super();
		this.simulationDao = new SimulationDao();
	}
	
	public List<Simulation> retrieveSimulations() throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return simulationDao.retrieve(entityManagerHandler);
		}
	}
	
	public List<Simulation> getSimulationAndParticipantsByUserId(User user) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return simulationDao.getSimulationAndParticipantsByUserId(user, entityManagerHandler);
		}
	}
	
	public Simulation retrieveBySimulationId(int simulationId) throws SQLException{
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return simulationDao.retrieveBySimulationId(simulationId, entityManagerHandler);
		}
	}
	
	
	
}
