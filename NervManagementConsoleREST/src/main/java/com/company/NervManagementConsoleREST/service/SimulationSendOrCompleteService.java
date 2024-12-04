package com.company.NervManagementConsoleREST.service;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.utils.CalculateUtils;
import com.company.NervManagementConsoleREST.utils.LevelUpUtils;

public class SimulationSendOrCompleteService {
	private SimulationService simulationService;
	private MemberService memberService;
	private UserMemberStatsService userMemberStatsService;
	private SimulationParticipantsService simulationParticipantsService;
	private final RetriveInformationService retriveInformationService;

	public SimulationSendOrCompleteService() {
		super();
		this.simulationService=new SimulationService();
		this.simulationParticipantsService = new SimulationParticipantsService();
		this.memberService=new MemberService();
		this.userMemberStatsService=new UserMemberStatsService();
		this.retriveInformationService = new RetriveInformationService();
	}

	public User sendMemberSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {
		int idMember = Integer.parseInt(idStringMember);
		Member member = memberService.retrieveByMemberId(idMember);
		int idSimulation = Integer.parseInt(idStringSimulation);
		Simulation simulation = simulationService.retrieveBySimulationId(idSimulation);
		LocalDateTime startTime = LocalDateTime.now();

		int duration = simulation.getDurationTime();

		LocalDateTime endTime = startTime.plusMinutes(duration);

		userMemberStatsService.updateMembStatsStartSim(user, member);
		SimulationParticipant simParticipant = new SimulationParticipant(simulation, user, member, startTime, endTime);
		simulationParticipantsService.createParticipant(simParticipant);
		user=retriveInformationService.retriveUserInformation(user);
		return user;
	}
	
	public User completeSimulation (User user, String idStringMember, String idStringSimulation) throws SQLException {	
			SimulationParticipant simPart;
			int idMember = Integer.parseInt(idStringMember);
			Member member = memberService.retrieveByMemberId(idMember);
			int idSimulation = Integer.parseInt(idStringSimulation);
			Simulation simulation = simulationService.retrieveBySimulationId(idSimulation);
			
			simPart= simulationParticipantsService.getParticipantbyUserAndMemberId(user, member);
			if(simPart.getEndTime().isBefore(LocalDateTime.now())) {

				UserMembersStats ums;
				ums= userMemberStatsService.retrieveStatsByUserAndMember(user, member);

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

				userMemberStatsService.updateMembStatsCompletedSim(user, member, ums);
				simulationParticipantsService.removeParticipant(user, simulation);
			}
			user=retriveInformationService.retriveUserInformation(user);
			return user;
		}
	
}
