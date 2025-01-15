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

import com.company.NervManagementConsoleREST.dao.service.MissionServiceDao;
import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.service.MissionService;

public class MissionServiceTest {
	private MissionService missionService;
	
	//oggetto mock
	private MissionServiceDao missionServiceDao = mock(MissionServiceDao.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionService = new MissionService();
	    }
		
		Field f = missionService.getClass().getDeclaredField("missionServiceDao");
		f.setAccessible(true);
		f.set(missionService, missionServiceDao);
	}
	
	@Test
	public void shouldReturnListMission_whenAllOk() throws Exception{
		//parameters
		List<Mission> missionList = new ArrayList<Mission>();
		
		//mocks
		doReturn(missionList).when(missionServiceDao).retrieveMissions();
		
		//test
		List<Mission> result = missionService.retrieveMissions();
		
		//results
		verify(missionServiceDao).retrieveMissions();
		assertNotNull(result);
		assertEquals(missionList, result);
	}
	
	@Test
	public void shouldReturnMissionByMissionId_whenAllOk() throws Exception{
		//parameters
		Mission mission = new Mission();
		
		//mocks
		doReturn(mission).when(missionServiceDao).getMissionById(anyInt());
		
		//test
		Mission result = missionService.getMissionById(123);
		
		//results
		verify(missionServiceDao).getMissionById(anyInt());
		assertNotNull(result);
		assertEquals(mission, result);
	}
	
	@Test
	public void shouldAddMission_whenAllOk() throws Exception{
		//parameters
		Mission mission = new Mission();
		
		//mocks
		doNothing().when(missionServiceDao).addMission(mission);
		
		//test
		missionService.addMission(mission);
		
		//verify
		verify(missionServiceDao).addMission(mission);
	}
	
	@Test
	public void shouldUpdateMission_whenAllOk() throws Exception{
		//parameters
		Mission mission = new Mission();
		
		//mocks
		doNothing().when(missionServiceDao).updateMission(mission);
		
		//test
		missionService.updateMission(mission);
		
		//verify
		verify(missionServiceDao).updateMission(mission);
	}
	
	@Test
	public void shouldAddEventMission_whenAllOk() throws Exception{
		//parameters
		Mission mission = new Mission();
		
		//mocks
		doNothing().when(missionServiceDao).addEventMission(mission);
		
		//test
		missionService.addEventMission(mission);
		
		//verify
		verify(missionServiceDao).addEventMission(mission);
	}
	
}
