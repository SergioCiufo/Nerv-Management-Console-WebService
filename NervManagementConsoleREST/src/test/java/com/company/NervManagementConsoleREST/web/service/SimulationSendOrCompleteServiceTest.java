package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.service.MemberService;
import com.company.NervManagementConsoleREST.service.RetriveInformationService;
import com.company.NervManagementConsoleREST.service.SimulationParticipantsService;
import com.company.NervManagementConsoleREST.service.SimulationSendOrCompleteService;
import com.company.NervManagementConsoleREST.service.SimulationService;
import com.company.NervManagementConsoleREST.service.UserMemberStatsService;

public class SimulationSendOrCompleteServiceTest {
	private SimulationSendOrCompleteService simulationSendOrCompleteService;

	//oggetto mock
	private SimulationService simulationService = mock(SimulationService.class);
	private MemberService memberService = mock(MemberService.class);
	private UserMemberStatsService userMemberStatsService = mock(UserMemberStatsService.class);
	private SimulationParticipantsService simulationParticipantsService=mock(SimulationParticipantsService.class);
	private RetriveInformationService retriveInformationService=mock(RetriveInformationService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			simulationSendOrCompleteService = new SimulationSendOrCompleteService();
	    }
		
		Field simulationServiceField = simulationSendOrCompleteService.getClass().getDeclaredField("simulationService");
		simulationServiceField.setAccessible(true);
		simulationServiceField.set(simulationSendOrCompleteService, simulationService);

		Field memberServiceField= simulationSendOrCompleteService.getClass().getDeclaredField("memberService");
		memberServiceField.setAccessible(true);
		memberServiceField.set(simulationSendOrCompleteService, memberService);

		Field userMemberStatsServiceField= simulationSendOrCompleteService.getClass().getDeclaredField("userMemberStatsService");
		userMemberStatsServiceField.setAccessible(true);
		userMemberStatsServiceField.set(simulationSendOrCompleteService, userMemberStatsService);

		Field simulationParticipantsServiceField = simulationSendOrCompleteService.getClass().getDeclaredField("simulationParticipantsService");
		simulationParticipantsServiceField.setAccessible(true);
		simulationParticipantsServiceField.set(simulationSendOrCompleteService, simulationParticipantsService);

		Field retriveInformationServiceField = simulationSendOrCompleteService.getClass().getDeclaredField("retriveInformationService");
		retriveInformationServiceField.setAccessible(true);
		retriveInformationServiceField.set(simulationSendOrCompleteService, retriveInformationService);
	}

	@Test
	public void shouldSendMemberToSimulation_whenAllOk() throws Exception{
		//parameters
		User user = new User();
		String idStringMember = "1";
		String idStringSimulation = "1";

		Member member = new Member();

		Simulation simulation = new Simulation();
		simulation.setDurationTime(30); // //exp min

		LocalDateTime startTime = LocalDateTime.now();
		LocalDateTime endTime = startTime.plusMinutes(simulation.getDurationTime());

		SimulationParticipant simParticipant = new SimulationParticipant(simulation, user, member, startTime, endTime);
		User updatedUser = new User(); // simulazione del risultato finale

		//mocks
		doReturn(member).when(memberService).retrieveByMemberId(anyInt());
		doReturn(simulation).when(simulationService).retrieveBySimulationId(anyInt());
		doNothing().when(userMemberStatsService).updateMembStatsStartSim(user, member);
		doNothing().when(simulationParticipantsService).createParticipant(simParticipant);
		doReturn(updatedUser).when(retriveInformationService).retriveUserInformation(user);

		//test
		User result = simulationSendOrCompleteService.sendMemberSimulation(user, idStringMember, idStringSimulation);

		//result
		verify(memberService).retrieveByMemberId(anyInt());
		verify(simulationService).retrieveBySimulationId(anyInt());
		verify(userMemberStatsService).updateMembStatsStartSim(user, member);
		verify(simulationParticipantsService).createParticipant(any(SimulationParticipant.class));
		verify(retriveInformationService).retriveUserInformation(user);

		assertNotNull(result);
		assertEquals(updatedUser, result);
	}

	@Test
	public void ShouldCompleteSimulation_whenPartecipantTimeIsBeforeLocalDateTimeNow() throws Exception {
		//parameters
		User user = new User();
		String idStringMember ="1";
		String idStringSimulation ="1";

		SimulationParticipant simPart = new SimulationParticipant();
		simPart.setEndTime(LocalDateTime.now().minusMinutes(10)); //ex

		Member member = new Member();

		Simulation simulation = new Simulation();
		simulation.setSupportAbility(50); //ex
		simulation.setSynchronizationRate(60); //ex
		simulation.setTacticalAbility(70); //ex
		simulation.setExp(100); //ex

		UserMembersStats ums = new UserMembersStats();
		ums.setSupportAbility(30); //ex
		ums.setSynchronizationRate(40); //ex
		ums.setTacticalAbility(50); //ex
		ums.setLevel(1); //ex

		//mocks
		doReturn(member).when(memberService).retrieveByMemberId(anyInt());
		doReturn(simulation).when(simulationService).retrieveBySimulationId(anyInt());
		doReturn(simPart).when(simulationParticipantsService).getParticipantbyUserAndMemberId(user, member);
			//dentro if
		doReturn(ums).when(userMemberStatsService).retrieveStatsByUserAndMember(user, member);
		doNothing().when(userMemberStatsService).updateMembStatsCompletedSim(user, member, ums);
		doNothing().when(simulationParticipantsService).removeParticipant(user, simulation);

		doReturn(user).when(retriveInformationService).retriveUserInformation(user);

		//test
		User result = simulationSendOrCompleteService.completeSimulation(user, idStringMember, idStringSimulation);

		//verify
		verify(memberService).retrieveByMemberId(anyInt());
		verify(simulationService).retrieveBySimulationId(anyInt());
		verify(simulationParticipantsService).getParticipantbyUserAndMemberId(user, member);
		verify(userMemberStatsService).retrieveStatsByUserAndMember(user, member);
		verify(userMemberStatsService).updateMembStatsCompletedSim(user, member, ums);
		verify(simulationParticipantsService).removeParticipant(user, simulation);
		verify(retriveInformationService).retriveUserInformation(user);
		assertNotNull(result);
		assertEquals(user, result);
	}
	
	@Test
	public void shouldDontcompleteSimulation_whenPartecipantTimeIsAfterLocalDateTimeNow() throws Exception {
		//parameters
		User user = new User();
		String idStringMember ="1";
		String idStringSimulation ="1";

		SimulationParticipant simPart = new SimulationParticipant();
		simPart.setEndTime(LocalDateTime.now().plusMinutes(10)); //ex

		Member member = new Member();

		Simulation simulation = new Simulation();
		simulation.setSupportAbility(50); //ex
		simulation.setSynchronizationRate(60); //ex
		simulation.setTacticalAbility(70); //ex
		simulation.setExp(100); //ex

		UserMembersStats ums = new UserMembersStats();
		ums.setSupportAbility(30); //ex
		ums.setSynchronizationRate(40); //ex
		ums.setTacticalAbility(50); //ex
		ums.setLevel(1); //ex

		//mocks
		doReturn(member).when(memberService).retrieveByMemberId(anyInt());
		doReturn(simulation).when(simulationService).retrieveBySimulationId(anyInt());
		doReturn(simPart).when(simulationParticipantsService).getParticipantbyUserAndMemberId(user, member);

		doReturn(user).when(retriveInformationService).retriveUserInformation(user);

		//test
		User result = simulationSendOrCompleteService.completeSimulation(user, idStringMember, idStringSimulation);

		//verify
		verify(memberService).retrieveByMemberId(anyInt());
		verify(simulationService).retrieveBySimulationId(anyInt());
		verify(simulationParticipantsService).getParticipantbyUserAndMemberId(user, member);
		verify(retriveInformationService).retriveUserInformation(user);
		assertNotNull(result);
		assertEquals(user, result);
	}
}
