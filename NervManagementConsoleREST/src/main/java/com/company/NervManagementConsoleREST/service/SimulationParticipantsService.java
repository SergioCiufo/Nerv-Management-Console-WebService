package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;

import com.company.NervManagementConsoleREST.dao.service.SimulationParticipantsServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;

public class SimulationParticipantsService {
	private SimulationParticipantsServiceDao simulationParticipantsServiceDao;
	
	public SimulationParticipantsService() {
		this.simulationParticipantsServiceDao = new SimulationParticipantsServiceDao();
	}
	
	public void createParticipant(SimulationParticipant simulationParticipant) {
		simulationParticipantsServiceDao.createParticipant(simulationParticipant);
	}
	
	public SimulationParticipant getParticipantbyUserAndMemberId(User user, Member member) {
		return simulationParticipantsServiceDao.getParticipantbyUserAndMemberId(user, member);
	}
	
	public void removeParticipant(User user, Simulation simulation) {
		simulationParticipantsServiceDao.removeParticipant(user, simulation);
	}
	
}
