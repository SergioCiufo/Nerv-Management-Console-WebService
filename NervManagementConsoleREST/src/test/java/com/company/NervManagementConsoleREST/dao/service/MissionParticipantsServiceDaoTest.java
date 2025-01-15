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
import com.company.NervManagementConsoleREST.dao.atomic.MissionParticipantsDao;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.User;

public class MissionParticipantsServiceDaoTest {
	private MissionParticipantsServiceDao missionParticipantsServiceDao;

	//oggetto mock
	private MissionParticipantsDao missionParticipantsDao = mock(MissionParticipantsDao.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			missionParticipantsServiceDao = new MissionParticipantsServiceDao();
		}

		Field missionParticipantsDaoField = missionParticipantsServiceDao.getClass().getDeclaredField("missionParticipantsDao");
		missionParticipantsDaoField.setAccessible(true);
		missionParticipantsDaoField.set(missionParticipantsServiceDao, missionParticipantsDao);

		Field jpaUtilField = missionParticipantsServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(missionParticipantsServiceDao, jpaUtil);
	}

	@Test
	public void shouldReturnMissionParticipantsListByUserAndMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		MissionParticipants missionParticipants = new MissionParticipants();
		List<MissionParticipants> missionParticipantsList = new ArrayList<MissionParticipants>();
		missionParticipantsList.add(missionParticipants);

		User user = new User();
		Mission mission = new Mission();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(missionParticipantsList).when(missionParticipantsDao).getMissionParticipantsByUserIdAndMissionId(user, mission, entityManagerHandler);

		//test
		List<MissionParticipants> result = missionParticipantsServiceDao.getMissionParticipantsByUserIdAndMissionId(user, mission);

		//result
		assertNotNull(result);
		assertEquals(missionParticipantsList, result);
		verify(missionParticipantsDao, times(1)).getMissionParticipantsByUserIdAndMissionId(user, mission, entityManagerHandler);
	}

	@Test
	public void shouldStartMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		MissionParticipants missionParticipants = new MissionParticipants();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(missionParticipantsDao).startMission(missionParticipants, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();
		
		//test
		missionParticipantsServiceDao.startMission(missionParticipants);
		
		//result
		verify(missionParticipantsDao, times(1)).startMission(missionParticipants, entityManagerHandler);
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
	}
	
	@Test
	public void shouldRemoveParticipant_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		User user = new User();
		Mission mission = new Mission();
		
		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(missionParticipantsDao).removeParticipant(user, mission, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();
		
		//test
		missionParticipantsServiceDao.removeParticipant(user, mission);
		
		//result
		verify(missionParticipantsDao, times(1)).removeParticipant(user, mission, entityManagerHandler);
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
	}
	
	@Test
	public void shouldReturnMissionParticipantsListByUserId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		MissionParticipants missionParticipants = new MissionParticipants();
		List<MissionParticipants> missionParticipantsList = new ArrayList<MissionParticipants>();
		missionParticipantsList.add(missionParticipants);

		int userId = 1;

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(missionParticipantsList).when(missionParticipantsDao).getActiveMissionsByUserId(userId, entityManagerHandler);

		//test
		List<MissionParticipants> result = missionParticipantsServiceDao.getActiveMissionsByUserId(userId);

		//result
		assertNotNull(result);
		assertEquals(missionParticipantsList, result);
		verify(missionParticipantsDao, times(1)).getActiveMissionsByUserId(userId, entityManagerHandler);
	}
}
