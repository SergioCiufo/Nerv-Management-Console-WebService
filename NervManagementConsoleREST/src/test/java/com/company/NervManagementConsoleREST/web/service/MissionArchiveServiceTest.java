package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.company.NervManagementConsoleREST.dao.service.MissionArchiveServiceDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.MissionArchive.MissionResult;
import com.company.NervManagementConsoleREST.service.MemberService;
import com.company.NervManagementConsoleREST.service.MissionArchiveService;

public class MissionArchiveServiceTest {
	private MissionArchiveService missionArchiveService;

	//oggetto mock
	private MissionArchiveServiceDao missionArchiveServiceDao = mock(MissionArchiveServiceDao.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionArchiveService = new MissionArchiveService();
	    }
		
		Field f = missionArchiveService.getClass().getDeclaredField("missionArchiveServiceDao");
		f.setAccessible(true);
		f.set(missionArchiveService, missionArchiveServiceDao);
	}

	@Test
	public void shouldReturnListMissionArchiveByUserIdAndMission_whenAllOk() throws Exception{
		//parameters
		List<MissionArchive> missionArchiveList = new ArrayList<MissionArchive>();
		User user = new User();
		Mission mission = new Mission();

		//mocks
		doReturn(missionArchiveList).when(missionArchiveServiceDao).retriveByUserIdAndIdMission(user, mission);

		//test
		List<MissionArchive> result = missionArchiveService.retriveByUserIdAndIdMission(user, mission);

		//results
		verify(missionArchiveServiceDao).retriveByUserIdAndIdMission(user, mission);
		assertNotNull(result);
		assertEquals(missionArchiveList, result);
	}

	@Test
	public void shouldAddMissionArchive_whenAllOk() throws Exception{
		//parameters
		MissionArchive missionArchive = new MissionArchive();

		//mocks
		//il metodo è void, quindi non ha nulla da restituire, fai nulla etc
		doNothing().when(missionArchiveServiceDao).addMissionArchive(missionArchive);

		//test
		missionArchiveService.addMissionArchive(missionArchive);

		//results
		verify(missionArchiveServiceDao).addMissionArchive(missionArchive);
	}

	@Test
	public void shouldReturnMissionArchiveWithMissionResultProgress_whenAllOk() throws Exception{
		//parameters
		MissionArchive missionArchive = new MissionArchive();
		User user = new User();
		Mission mission = new Mission();
		
		//mocks
		doReturn(missionArchive).when(missionArchiveServiceDao).retriveByUserIdAndIdMissionResultProgress(user, mission);
		
		//test
		MissionArchive result  = missionArchiveService.retriveByUserIdAndIdMissionResultProgress(user, mission);
		
		//results
		verify(missionArchiveServiceDao).retriveByUserIdAndIdMissionResultProgress(user, mission);
		assertNotNull(result);
		assertEquals(missionArchive, result);
	}
	
	@Test
	public void shouldUpdateMissionResult_whenAllOk() throws Exception{
		//parameters
		MissionArchive missionArchive = new MissionArchive();
		//MissionResult missionResult = any(); //non funzionava bene, ho messo un valore fisso
		
		 // Mocks
	    doNothing().when(missionArchiveServiceDao).updateMissionResult(missionArchive, MissionResult.PROGRESS);
	    
	    // Test
	    missionArchiveService.updateMissionResult(missionArchive, MissionResult.PROGRESS);
	    
	    // Results
	    verify(missionArchiveServiceDao).updateMissionResult(missionArchive, MissionResult.PROGRESS);
	}
	
	@Test
	public void shouldReturnListMissionArchiveWithUserId_whenAllOk() throws Exception{
		//parameters
		List<MissionArchive> missionArchiveList = new ArrayList<MissionArchive>();
		//userId è stato messo come anyInt perché il valore può essere qualsiasi
		
		//mocks
		doReturn(missionArchiveList).when(missionArchiveServiceDao).retriveByUserId(anyInt());
		
		//test																//valore qualsiasi (qui non si può mettere anyint)
		List<MissionArchive> result = missionArchiveService.retriveByUserId(123);
		
		//results
		verify(missionArchiveServiceDao).retriveByUserId(anyInt());
		assertNotNull(result);
		assertEquals(missionArchiveList, result);
	}

}
