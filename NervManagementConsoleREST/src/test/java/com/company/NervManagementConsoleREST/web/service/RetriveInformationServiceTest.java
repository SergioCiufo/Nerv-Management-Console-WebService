package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.service.MemberService;
import com.company.NervManagementConsoleREST.service.MissionArchiveService;
import com.company.NervManagementConsoleREST.service.MissionParticipantsService;
import com.company.NervManagementConsoleREST.service.MissionService;
import com.company.NervManagementConsoleREST.service.RetriveInformationService;
import com.company.NervManagementConsoleREST.service.SimulationService;
import com.company.NervManagementConsoleREST.service.UserMemberStatsService;
import com.company.NervManagementConsoleREST.web.servlet.MissionCompletedServlet;

public class RetriveInformationServiceTest {
	private RetriveInformationService retriveInformationService;
	
	//oggetto mock
	private MemberService memberService =mock(MemberService.class);
	private UserMemberStatsService userMemberStatsService=mock(UserMemberStatsService.class);
	private SimulationService simulationService=mock(SimulationService.class);
	private MissionService missionService=mock(MissionService.class);
	private MissionParticipantsService missionParticipantsService=mock(MissionParticipantsService.class);
	private MissionArchiveService missionArchiveService=mock(MissionArchiveService.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			retriveInformationService = new RetriveInformationService();
	    }
		
		Field memberServiceField = retriveInformationService.getClass().getDeclaredField("memberService");
		memberServiceField.setAccessible(true);
		memberServiceField.set(retriveInformationService, memberService);
		
		Field userMemberStatsServiceField = retriveInformationService.getClass().getDeclaredField("userMemberStatsService");
		userMemberStatsServiceField.setAccessible(true);
		userMemberStatsServiceField.set(retriveInformationService, userMemberStatsService);
		
		Field simulationServiceField = retriveInformationService.getClass().getDeclaredField("simulationService");
		simulationServiceField.setAccessible(true);
		simulationServiceField.set(retriveInformationService, simulationService);
		
		Field missionServiceField = retriveInformationService.getClass().getDeclaredField("missionService");
		missionServiceField.setAccessible(true);
		missionServiceField.set(retriveInformationService, missionService);
		
		Field missionParticipantsServiceField = retriveInformationService.getClass().getDeclaredField("missionParticipantsService");
		missionParticipantsServiceField.setAccessible(true);
		missionParticipantsServiceField.set(retriveInformationService, missionParticipantsService);
		
		Field missionArchiveServiceField = retriveInformationService.getClass().getDeclaredField("missionArchiveService");
		missionArchiveServiceField.setAccessible(true);
		missionArchiveServiceField.set(retriveInformationService, missionArchiveService);
	}
	
	@Test
	public void shouldRetrieveUserInformation_whenAllOk() throws Exception {
	    //parameters
	    User user = new User();

	    List<Member> memberList = new ArrayList<>();
	    List<Simulation> simulationList = new ArrayList<>();
	    
	    Member member = new Member();
	    memberList.add(member);
	    
	    UserMembersStats stats = new UserMembersStats();
	    
	    Simulation simulation = new Simulation();
	    List<SimulationParticipant> participants = new ArrayList<>();
	    SimulationParticipant participant = new SimulationParticipant();
	    participants.add(participant); 
	    simulation.setSimulationParticipants(participants); 
	    simulationList.add(simulation); 

	    //mock
	    doReturn(memberList).when(memberService).retrieveMembers();
	    doReturn(simulationList).when(simulationService).retrieveSimulations();
	    doReturn(stats).when(userMemberStatsService).retrieveStatsByUserAndMember(user, member);
	    doReturn(simulationList).when(simulationService).getSimulationAndParticipantsByUserId(user);

	    List<Mission> missionList = new ArrayList<>();
	    List<MissionArchive> missionArchiveList = new ArrayList<>();
	    Mission mission = new Mission();
	    MissionArchive missionArchive = new MissionArchive();
	    missionList.add(mission);
	    missionArchive.setMissionCode("M1-001");
	    missionArchiveList.add(missionArchive);

	    doReturn(missionList).when(missionService).retrieveMissions();
	    doReturn(missionArchiveList).when(missionArchiveService).retriveByUserIdAndIdMission(user, mission);
	    doReturn(new ArrayList<MissionParticipants>()).when(missionParticipantsService).getMissionParticipantsByUserIdAndMissionId(user, mission);

	    //test
	    User resultUser = retriveInformationService.retriveUserInformation(user);

	    //results
	    verify(memberService).retrieveMembers();
	    verify(simulationService).retrieveSimulations();
	    verify(userMemberStatsService, times(2)).retrieveStatsByUserAndMember(user, member);
	    verify(simulationService).getSimulationAndParticipantsByUserId(user);
	    verify(missionService).retrieveMissions();
	    verify(missionArchiveService).retriveByUserIdAndIdMission(user, mission);
	    verify(missionParticipantsService).getMissionParticipantsByUserIdAndMissionId(user, mission);

	    assertEquals(1, resultUser.getParticipants().size()); 
	    assertEquals(participant, resultUser.getParticipants().get(0)); 

	    assertNotNull(resultUser.getMissionParticipants());
	    assertNotNull(resultUser.getMissionArchiveResult());

	    assertEquals(1, resultUser.getMissions().size());
	    assertEquals(mission, resultUser.getMissions().get(0));

	    assertEquals(1, resultUser.getMissionArchive().size());
	    assertEquals(missionArchive, resultUser.getMissionArchive().get(0));

	    assertNotNull(resultUser.getMissionArchiveResult());
	    //assertTrue(resultUser.getMissionArchiveResult().containsKey("mission"));
	    //assertEquals(1, resultUser.getMissionArchiveResult().get("mission").size());
	}
	
	@Test
	public void shouldHandleNullMemberList_whenAllOk() throws Exception {
	    //mock
	    doReturn(null).when(memberService).retrieveMembers();
	    doReturn(new ArrayList<>()).when(simulationService).retrieveSimulations();

	    //test
	    User resultUser = retriveInformationService.retriveUserInformation(new User());

	    //results
	    verify(memberService).retrieveMembers();
	    verify(simulationService).retrieveSimulations();
	    assertNotNull(resultUser);
	}
}
