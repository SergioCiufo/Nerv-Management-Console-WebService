package com.company.NervManagementConsoleREST.dao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
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

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.config.JpaUtil;
import com.company.NervManagementConsoleREST.dao.atomic.MissionArchiveDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionArchive.MissionResult;
import com.company.NervManagementConsoleREST.model.User;

public class MissionArchiveServiceDaoTest {
	private MissionArchiveServiceDao missionArchiveServiceDao;

	//oggetto mock
	private MissionArchiveDao missionArchiveDao = mock(MissionArchiveDao.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionArchiveServiceDao = new MissionArchiveServiceDao();
		}

		Field missionArchiveDaoField = missionArchiveServiceDao.getClass().getDeclaredField("missionArchiveDao");
		missionArchiveDaoField.setAccessible(true);
		missionArchiveDaoField.set(missionArchiveServiceDao, missionArchiveDao);

		Field jpaUtilField = missionArchiveServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(missionArchiveServiceDao, jpaUtil);
	}

	@Test
	public void shouldReturnListMissionArchiveByUserAndMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		MissionArchive missionArchive = new MissionArchive();
		List<MissionArchive> missionArchiveList = new ArrayList<MissionArchive>();

		User user = new User();
		Mission mission = new Mission();

		//mokc
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(missionArchiveList).when(missionArchiveDao).retriveByUserIdAndIdMission(user, mission, entityManagerHandler);

		//test
		List<MissionArchive> result = missionArchiveServiceDao.retriveByUserIdAndIdMission(user, mission);

		//result
		assertNotNull(result);
		assertEquals(missionArchiveList, result);
		verify(missionArchiveDao, times(1)).retriveByUserIdAndIdMission(user, mission, entityManagerHandler);
	}

	@Test
	public void shouldAddMissionArchive_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		MissionArchive missionArchive = new MissionArchive();
		
		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(missionArchiveDao).addMissionArchive(missionArchive, entityManagerHandler);
		
		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();
		
		//test
		missionArchiveServiceDao.addMissionArchive(missionArchive);
		
		//result
		verify(missionArchiveDao, times(1)).addMissionArchive(missionArchive, entityManagerHandler);
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
	}
	
	@Test
	public void shouldReturnMissionArchiveByUserAndMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		MissionArchive missionArchive = new MissionArchive();
		
		User user = new User();
		Mission mission = new Mission();

		//mokc
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(missionArchive).when(missionArchiveDao).retriveByUserIdAndIdMissionResultProgress(user, mission, entityManagerHandler);

		//test
		MissionArchive result = missionArchiveServiceDao.retriveByUserIdAndIdMissionResultProgress(user, mission);

		//result
		assertNotNull(result);
		assertEquals(missionArchive, result);
		verify(missionArchiveDao, times(1)).retriveByUserIdAndIdMissionResultProgress(user, mission, entityManagerHandler);
	}
	
	@Test
	public void shouldUpdateMissionResult_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		MissionArchive missionArchive = new MissionArchive();
		
		MissionResult missionResult = null;
		
		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(missionArchiveDao).updateMissionResult(missionArchive, missionResult, entityManagerHandler);
		
		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();
		
		//test
		missionArchiveServiceDao.updateMissionResult(missionArchive, missionResult);
		
		//result
		verify(missionArchiveDao, times(1)).updateMissionResult(missionArchive, missionResult, entityManagerHandler);
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
	}
	
	@Test
	public void shouldReturnListMissionArchiveByUserId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		MissionArchive missionArchive = new MissionArchive();
		List<MissionArchive> missionArchiveList = new ArrayList<MissionArchive>();

		int userId= 1;

		//mokc
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(missionArchiveList).when(missionArchiveDao).retriveByUserId(userId, entityManagerHandler);

		//test
		List<MissionArchive> result = missionArchiveServiceDao.retriveByUserId(userId);

		//result
		assertNotNull(result);
		assertEquals(missionArchiveList, result);
		verify(missionArchiveDao, times(1)).retriveByUserId(userId, entityManagerHandler);
	}
}
