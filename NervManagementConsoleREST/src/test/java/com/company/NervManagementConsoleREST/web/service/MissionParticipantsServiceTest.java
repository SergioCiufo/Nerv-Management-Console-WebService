package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.MissionParticipantsServiceDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.service.MissionArchiveService;
import com.company.NervManagementConsoleREST.service.MissionParticipantsService;

public class MissionParticipantsServiceTest {
	private MissionParticipantsService missionParticipantsService;
		
	//oggetto mock
	private MissionParticipantsServiceDao missionParticipantsServiceDao = mock(MissionParticipantsServiceDao.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionParticipantsService = new MissionParticipantsService();
	    }
		
		Field f = missionParticipantsService.getClass().getDeclaredField("missionParticipantsServiceDao");
		f.setAccessible(true);
		f.set(missionParticipantsService, missionParticipantsServiceDao);
	}
	
	@Test
	public void shouldReturnListMissionPartecipantByUserIdAndMissionId_whenAllOk() throws Exception{
		//parameters
		List<MissionParticipants> missionPartecipantsList = new ArrayList<MissionParticipants>();
		User user = new User();
		Mission mission = new Mission();
		
		//mocks
		doReturn(missionPartecipantsList).when(missionParticipantsServiceDao).getMissionParticipantsByUserIdAndMissionId(user, mission);
		
		//test
		List<MissionParticipants> result = missionParticipantsService.getMissionParticipantsByUserIdAndMissionId(user, mission);
		
		//results
		verify(missionParticipantsServiceDao).getMissionParticipantsByUserIdAndMissionId(user, mission);
		assertNotNull(result);
		assertEquals(missionPartecipantsList, result);
	}
	
	@Test
	public void shouldStartMission_whenAllOk() throws Exception{
		//parameters
		MissionParticipants missionParticipants = new MissionParticipants();
		
		//mocks
		doNothing().when(missionParticipantsServiceDao).startMission(missionParticipants);
		
		//test
		missionParticipantsService.startMission(missionParticipants);
		
		//results
		verify(missionParticipantsServiceDao).startMission(missionParticipants);
	}
	
	@Test
	public void shouldRemoveParticipant_whenAllOk() throws Exception{
		//parameters
		MissionParticipants missionParticipants = new MissionParticipants();
		User user = new User();
		Mission mission = new Mission();
		
		//mocks
		doNothing().when(missionParticipantsServiceDao).removeParticipant(user, mission);
		
		//test
		missionParticipantsService.removeParticipant(user, mission);
		
		//results
		verify(missionParticipantsServiceDao).removeParticipant(user, mission);
	}
	
	@Test
	public void shouldReturnListMissionParticipantsOfActiveMissionByUser_whenAllOk() throws Exception{
		//parameters
		List<MissionParticipants> missionParticipantsList = new ArrayList<MissionParticipants>();
		//mocks
		doReturn(missionParticipantsList).when(missionParticipantsServiceDao).getActiveMissionsByUserId(anyInt());
		
		//test
		List<MissionParticipants> result = missionParticipantsService.getActiveMissionsByUserId(123);
		
		//results
		verify(missionParticipantsServiceDao).getActiveMissionsByUserId(anyInt());
		assertNotNull(result);
		assertEquals(missionParticipantsList, result);
	}
}
