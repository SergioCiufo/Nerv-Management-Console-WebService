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
	/*
	private MemberService memberService;
	private UserMemberStatsService userMemberStatsService;
	private SimulationParticipantsService simulationParticipantsService;
*/

	public SimulationService() {
		super();
		this.simulationServiceDao = new SimulationServiceDao();
		/*
		this.memberService = new MemberService();
		this.userMemberStatsService = new UserMemberStatsService();
		this.simulationParticipantsService = new SimulationParticipantsService();
		*/
	}
	
	public List<Simulation> retrieveSimulations() throws SQLException{
		return simulationServiceDao.retrieveSimulations();
	}
	
	public List<Simulation> getSimulationAndParticipantsByUserId(User user) throws SQLException{
		return simulationServiceDao.getSimulationAndParticipantsByUserId(user);
	}
	
	public Simulation retrieveBySimulationId(int idSimulation) throws SQLException{
		return simulationServiceDao.retrieveBySimulationId(idSimulation);
	}

/*
	public User completeSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {
		try(EntityManagerHandler entityManagerHandler = JpaUtil.getEntityManager()){
			entityManagerHandler.beginTransaction();
			
			SimulationParticipant simPart;
			int idMember = Integer.parseInt(idStringMember);
			Member member = memberDao.retrieveByMemberId(idMember, entityManagerHandler);
			int idSimulation = Integer.parseInt(idStringSimulation);
			Simulation simulation = simulationDao.retrieveBySimulationId(idSimulation, entityManagerHandler);
			
			simPart= simulationParticipantsDao.getParticipantbyUserAndMemberId(user, member, entityManagerHandler);
			if(simPart.getEndTime().isBefore(LocalDateTime.now())) {

				UserMembersStats ums;
				ums= userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);

				Integer suppAbility = simulation.getSupportAbility();
				suppAbility = CalculateUtils.randomizeStats(suppAbility);
				suppAbility =(suppAbility+ums.getSupportAbility());
				suppAbility = CalculateUtils.MinMaxStat(suppAbility);
				ums.setSupportAbility(suppAbility);

				Integer sincRate = simulation.getSynchronizationRate();
				sincRate = CalculateUtils.randomizeStats(sincRate);
				sincRate = (sincRate+ums.getSynchronizationRate());
				sincRate = CalculateUtils.MinMaxStat(sincRate);
				ums.setSynchronizationRate(sincRate);

				Integer tactAbility = simulation.getTacticalAbility();
				tactAbility= CalculateUtils.randomizeStats(tactAbility);
				tactAbility = (tactAbility+ums.getTacticalAbility());
				tactAbility = CalculateUtils.MinMaxStat(tactAbility);
				ums.setTacticalAbility(tactAbility);

				Integer newExp =simulation.getExp();
				newExp = CalculateUtils.randomizeStats(newExp);

				ums=LevelUpUtils.levelUp(ums, newExp);

				userMemberStatsDao.updateMembStatsCompletedSim(user, member, ums, entityManagerHandler);
				simulationParticipantsDao.removeParticipant(user, simulation, entityManagerHandler);

				entityManagerHandler.commitTransaction();
			}
			user=ris.retriveUserInformation(user, entityManagerHandler);
			return user;
		}
	}
	*/
}
