package com.company.NervManagementConsoleREST.dao.service;

import java.sql.SQLException;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.SimulationParticipantsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;

public class SimulationParticipantsServiceDao {
	private SimulationParticipantsDao simulationParticipantsDao;
	
	public SimulationParticipantsServiceDao() {
		this.simulationParticipantsDao = new SimulationParticipantsDao();
	}
	
	public void createParticipant(SimulationParticipant simulationParticipant){
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			simulationParticipantsDao.createParticipant(simulationParticipant, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
	
	public SimulationParticipant getParticipantbyUserAndMemberId(User user, Member member) {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			return simulationParticipantsDao.getParticipantbyUserAndMemberId(user, member, entityManagerHandler);
		}
	}
	
	public void removeParticipant (User user, Simulation simulation) {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			simulationParticipantsDao.removeParticipant(user, simulation, entityManagerHandler);
			entityManagerHandler.commitTransaction();
		}
	}
}
