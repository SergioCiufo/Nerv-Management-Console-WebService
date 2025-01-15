package com.company.NervManagementConsoleREST.serviceSOAP;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.service.MissionService;

public class MissionEventServiceSoapTest {
	private MissionEventServiceSoap missionEventServiceSoap;

	//oggetto mock
	private MissionService missionService = mock(MissionService.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionEventServiceSoap = new MissionEventServiceSoap();
	    }
		
		Field f = missionEventServiceSoap.getClass().getDeclaredField("missionService");
		f.setAccessible(true);
		f.set(missionEventServiceSoap, missionService);
	}

	@Test
	public void shouldAddMissionEventSuccessfully() {
		//parameters
		Mission mission = new Mission();

		//mock
		doNothing().when(missionService).addEventMission(mission);

		//test
		Mission result = missionEventServiceSoap.addMissionEvent(mission);

		//result
		assertNotNull(result);
		assertEquals(mission, result);
		verify(missionService, times(1)).addEventMission(mission);
	}

	@Test
	public void shouldReturnNull_whenAddMissionEventFails() {
		//parameters
		Mission mission = new Mission();

		//mock
		doThrow(new RuntimeException("Error while adding event")).when(missionService).addEventMission(mission);

		//test
		Mission result = missionEventServiceSoap.addMissionEvent(mission);
		
		//result
		assertNull(result);
		verify(missionService, times(1)).addEventMission(mission);
	}

}
