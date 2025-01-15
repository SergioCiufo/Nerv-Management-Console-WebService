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
import com.company.NervManagementConsoleREST.dao.atomic.MissionDao;
import com.company.NervManagementConsoleREST.model.Mission;

public class MissionServiceDaoTest {
	private MissionServiceDao missionServiceDao;

	//oggetto mock
	private MissionDao missionDao = mock(MissionDao.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionServiceDao = new MissionServiceDao();
		}

		Field missionDaoField = missionServiceDao.getClass().getDeclaredField("missionDao");
		missionDaoField.setAccessible(true);
		missionDaoField.set(missionServiceDao, missionDao);

		Field jpaUtilField = missionServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(missionServiceDao, jpaUtil);
	}

	@Test
	public void shouldReturnMissionList_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Mission mission = new Mission();
		List<Mission> missionList = new ArrayList<Mission>();
		missionList.add(mission);

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(missionList).when(missionDao).retrieve(entityManagerHandler);

		//test
		List<Mission> result = missionServiceDao.retrieveMissions();

		//result
		assertNotNull(result);
		assertEquals(missionList, result);
		verify(missionDao, times(1)).retrieve(entityManagerHandler);
	}

	@Test
	public void shouldReturnMissionByIdMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Mission mission = new Mission();

		int idMission =1;

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(mission).when(missionDao).getMissionById(idMission, entityManagerHandler);

		//test
		Mission result = missionServiceDao.getMissionById(idMission);

		//result
		assertNotNull(result);
		assertEquals(mission, result);
		verify(missionDao, times(1)).getMissionById(idMission, entityManagerHandler);
	}

	@Test
	public void shouldAddMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Mission mission = new Mission();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(missionDao).create(mission, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		missionServiceDao.addMission(mission);

		//result
		verify(missionDao, times(1)).create(mission, entityManagerHandler);
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
	}

	@Test
	public void shouldUpdateMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Mission mission = new Mission();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(missionDao).updateMission(mission, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		missionServiceDao.updateMission(mission);

		//result
		verify(missionDao, times(1)).updateMission(mission, entityManagerHandler);
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
	}

	@Test
	public void shouldAddEventMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Mission mission = new Mission();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(missionDao).updateEventMissionByAvailableTrue(entityManagerHandler);
		doNothing().when(missionDao).create(mission, entityManagerHandler);
		
		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		missionServiceDao.addEventMission(mission);

		//result
		verify(missionDao, times(1)).updateEventMissionByAvailableTrue(entityManagerHandler);
		verify(missionDao, times(1)).create(mission, entityManagerHandler);
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
	}
}
