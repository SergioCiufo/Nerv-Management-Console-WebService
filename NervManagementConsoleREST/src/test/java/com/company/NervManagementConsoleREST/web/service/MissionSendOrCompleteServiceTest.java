package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionArchive.MissionResult;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.service.MissionArchiveService;
import com.company.NervManagementConsoleREST.service.MissionParticipantsService;
import com.company.NervManagementConsoleREST.service.MissionSendOrCompleteService;
import com.company.NervManagementConsoleREST.service.MissionService;
import com.company.NervManagementConsoleREST.service.RetriveInformationService;
import com.company.NervManagementConsoleREST.service.UserMemberStatsService;
import com.company.NervManagementConsoleREST.utils.LevelUpUtils;
import com.company.NervManagementConsoleREST.utils.MissionResultUtils;

public class MissionSendOrCompleteServiceTest {
	private MissionSendOrCompleteService missionSendOrCompleteService;

	//oggetto mock
	private MissionService missionService = mock(MissionService.class);
	private UserMemberStatsService userMemberStatsService=mock(UserMemberStatsService.class);
	private MissionParticipantsService missionParticipantsService=mock(MissionParticipantsService.class);
	private MissionArchiveService missionArchiveService=mock(MissionArchiveService.class);
	private RetriveInformationService retriveInformationService=mock(RetriveInformationService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionSendOrCompleteService = new MissionSendOrCompleteService();
	    }
		
		Field missionServiceField = missionSendOrCompleteService.getClass().getDeclaredField("missionService");
		missionServiceField.setAccessible(true);
		missionServiceField.set(missionSendOrCompleteService, missionService);

		Field userMemberStatsServiceField = missionSendOrCompleteService.getClass().getDeclaredField("userMemberStatsService");
		userMemberStatsServiceField.setAccessible(true);
		userMemberStatsServiceField.set(missionSendOrCompleteService, userMemberStatsService);

		Field missionParticipantsServiceField = missionSendOrCompleteService.getClass().getDeclaredField("missionParticipantsService");
		missionParticipantsServiceField.setAccessible(true);
		missionParticipantsServiceField.set(missionSendOrCompleteService, missionParticipantsService); 

		Field missionArchiveServiceField = missionSendOrCompleteService.getClass().getDeclaredField("missionArchiveService");
		missionArchiveServiceField.setAccessible(true);
		missionArchiveServiceField.set(missionSendOrCompleteService, missionArchiveService); 

		Field retriveInformationServiceField = missionSendOrCompleteService.getClass().getDeclaredField("retriveInformationService");
		retriveInformationServiceField.setAccessible(true);
		retriveInformationServiceField.set(missionSendOrCompleteService, retriveInformationService); 
	}

	//any meglio mai utilizzarli
	//oggetto o valore da testare ma non any

	@Test
	public void shouldSendMemberToMission_whenAllOk() throws Exception {
		//parameters
		User user = new User();
		String idMissionString = "1";
		List<String> idMemberString = Arrays.asList("1");

		Mission mission = mock(Mission.class);
		UserMembersStats stats = mock(UserMembersStats.class);
		MissionArchive missionArchive = mock(MissionArchive.class);
		MissionParticipants missionParticipants = mock(MissionParticipants.class);

		List<Member> members = new ArrayList<>();
		Member member1 = mock(Member.class);
		members.add(member1);

		user.setMembers(members);

		List<MissionArchive> missionArch = new ArrayList<>();

		//mocks
		doReturn(mission).when(missionService).getMissionById(anyInt());
		doReturn(10).when(mission).getDurationTime();

		doReturn(missionArch).when(missionArchiveService).retriveByUserIdAndIdMission(user, mission);
		doReturn(stats).when(member1).getMemberStats();

		doNothing().when(missionArchiveService).addMissionArchive(any(MissionArchive.class));
		doNothing().when(missionParticipantsService).startMission(any(MissionParticipants.class));
		doNothing().when(userMemberStatsService).updateMembStatsStartSim(any(User.class), any(Member.class));

		doReturn(user).when(retriveInformationService).retriveUserInformation(user);

		doReturn(1).when(member1).getIdMember();

		//test
		User resultUser = missionSendOrCompleteService.sendMembersMission(user, idMissionString, idMemberString);

		//result	    
		assertNotNull(idMemberString);
		verify(member1).getIdMember();
		verify(missionService).getMissionById(anyInt());
		verify(missionArchiveService).retriveByUserIdAndIdMission(user, mission);
		assertNotNull(stats);
		assertNotNull(user);
		assertNotNull(missionArchive);
		verify(missionArchiveService).addMissionArchive(any(MissionArchive.class));
		verify(missionParticipantsService).startMission(any(MissionParticipants.class));
		verify(userMemberStatsService).updateMembStatsStartSim(any(User.class), any(Member.class));

		verify(retriveInformationService).retriveUserInformation(user);

		assertEquals(user, resultUser);
	}

	@Test
	public void shouldCompleteMissionResultWin_whenAllOk() throws Exception {
	    //parameters
	    User user = new User();
	    String idMissionString = "1";
	    
	    Member member = mock(Member.class);
	    Mission mission = mock(Mission.class);
	    MissionArchive missionArchive = mock(MissionArchive.class);
	    MissionParticipants missionParticipants = mock(MissionParticipants.class);
	    UserMembersStats ums = mock(UserMembersStats.class);
	    LevelUpUtils levelUpUtils = mock(LevelUpUtils.class);
	    MissionResultUtils missionResultUtils = mock(MissionResultUtils.class);
	    
	    
	    int newExp = 10;
	    
	    List<MissionParticipants> missionParticipantsList = new ArrayList<>();
	    missionParticipantsList.add(missionParticipants);
	    
	    List<UserMembersStats> umsList = new ArrayList<UserMembersStats>();
	    umsList.add(ums);

	    //mocks
	    doReturn(mission).when(missionService).getMissionById(1);
	    doReturn(missionArchive).when(missionArchiveService).retriveByUserIdAndIdMissionResultProgress(user, mission);
	    doReturn(missionParticipantsList).when(missionParticipantsService).getMissionParticipantsByUserIdAndMissionId(user, mission);

	    doReturn(member).when(missionParticipants).getMember();
	    
	    doReturn(ums).when(userMemberStatsService).retrieveStatsByUserAndMember(user, member);
	    doNothing().when(userMemberStatsService).updateMembStatsCompletedMission(ums);
	    doNothing().when(missionParticipantsService).removeParticipant(user, mission);
	    doReturn(user).when(retriveInformationService).retriveUserInformation(user);

	    doReturn(ums).when(levelUpUtils).levelUp(ums, newExp);
	    
	    doReturn(true).when(missionResultUtils).missionResult(mission, 1, umsList);	   
	    
	    //test
	    User resultUser = missionSendOrCompleteService.completeMission(user, idMissionString);

	    //results
	    verify(missionService).getMissionById(1);
	    verify(missionArchiveService).retriveByUserIdAndIdMissionResultProgress(user, mission);
	    verify(userMemberStatsService).retrieveStatsByUserAndMember(user, member);
	    verify(userMemberStatsService).updateMembStatsCompletedMission(ums);
	    verify(missionParticipantsService).removeParticipant(user, mission);
	    verify(retriveInformationService).retriveUserInformation(user);

	    assertEquals(user, resultUser);
	}
	
	@Test
	public void shouldCompleteMissionResultLose_whenAllOk() throws Exception {
	    //parameters
	    User user = new User();
	    String idMissionString = "1";
	    
	    Member member = mock(Member.class);
	    Mission mission = mock(Mission.class);
	    MissionArchive missionArchive = mock(MissionArchive.class);
	    MissionParticipants missionParticipants = mock(MissionParticipants.class);
	    UserMembersStats ums = mock(UserMembersStats.class);
	    LevelUpUtils levelUpUtils = mock(LevelUpUtils.class);
	    MissionResultUtils missionResultUtils = mock(MissionResultUtils.class);
	    
	    
	    int newExp = 10;
	    
	    List<MissionParticipants> missionParticipantsList = new ArrayList<>();
	    missionParticipantsList.add(missionParticipants);
	    
	    List<UserMembersStats> umsList = new ArrayList<UserMembersStats>();
	    umsList.add(ums);

	    //mocks
	    doReturn(mission).when(missionService).getMissionById(1);
	    doReturn(missionArchive).when(missionArchiveService).retriveByUserIdAndIdMissionResultProgress(user, mission);
	    doReturn(missionParticipantsList).when(missionParticipantsService).getMissionParticipantsByUserIdAndMissionId(user, mission);

	    doReturn(member).when(missionParticipants).getMember();
	    
	    doReturn(ums).when(userMemberStatsService).retrieveStatsByUserAndMember(user, member);
	    doNothing().when(userMemberStatsService).updateMembStatsCompletedMission(ums);
	    doNothing().when(missionParticipantsService).removeParticipant(user, mission);
	    doReturn(user).when(retriveInformationService).retriveUserInformation(user);

	    doReturn(ums).when(levelUpUtils).levelUp(ums, newExp);
	    
	    doReturn(false).when(missionResultUtils).missionResult(mission, 1, umsList);	   
	    
	    //test
	    User resultUser = missionSendOrCompleteService.completeMission(user, idMissionString);

	    //results
	    verify(missionService).getMissionById(1);
	    verify(missionArchiveService).retriveByUserIdAndIdMissionResultProgress(user, mission);
	    verify(userMemberStatsService).retrieveStatsByUserAndMember(user, member);
	    verify(userMemberStatsService).updateMembStatsCompletedMission(ums);
	    verify(missionParticipantsService).removeParticipant(user, mission);
	    verify(retriveInformationService).retriveUserInformation(user);

	    assertEquals(user, resultUser);
	}

}
