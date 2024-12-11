package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.service.SimulationServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.utils.CalculateUtils;
import com.company.NervManagementConsoleREST.utils.LevelUpUtils;

public class SimulationService {
	private SimulationServiceDao simulationServiceDao;

	public SimulationService() {
		super();
		this.simulationServiceDao = new SimulationServiceDao();
	}
	
	public List<Simulation> retrieveSimulations() {
		return simulationServiceDao.retrieveSimulations();
	}
	
	public List<Simulation> getSimulationAndParticipantsByUserId(User user) {
		return simulationServiceDao.getSimulationAndParticipantsByUserId(user);
	}
	
	public Simulation retrieveBySimulationId(int idSimulation) {
		return simulationServiceDao.retrieveBySimulationId(idSimulation);
	}

}
