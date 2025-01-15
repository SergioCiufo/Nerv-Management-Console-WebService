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
	private JpaUtil jpaUtil;

	public SimulationServiceDao() {
		super();
		this.simulationDao = new SimulationDao();
		this.jpaUtil = new JpaUtil();
	}
	
	public List<Simulation> retrieveSimulations() {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			return simulationDao.retrieve(entityManagerHandler);
		}
	}
	
	public List<Simulation> getSimulationAndParticipantsByUserId(User user) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			return simulationDao.getSimulationAndParticipantsByUserId(user, entityManagerHandler);
		}
	}
	
	public Simulation retrieveBySimulationId(int simulationId) {
		try(EntityManagerHandler entityManagerHandler = jpaUtil.getEntityManager()){
			return simulationDao.retrieveBySimulationId(simulationId, entityManagerHandler);
		}
	}
	
	
	
}
