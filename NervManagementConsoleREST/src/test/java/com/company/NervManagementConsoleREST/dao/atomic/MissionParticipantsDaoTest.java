package com.company.NervManagementConsoleREST.dao.atomic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.User;

public class MissionParticipantsDaoTest {
	private MissionParticipantsDao missionParticipantsDao = new MissionParticipantsDao();

	@Test
	public void shouldStartMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionParticipants missionParticipants = new MissionParticipants();

		//mock
		doNothing().when(entityManagerHandler).persist(missionParticipants);

		//test
		missionParticipantsDao.startMission(missionParticipants, entityManagerHandler);

		//result
		verify(entityManagerHandler, times(1)).persist(missionParticipants);
	}

	@Test
	public void shouldThrowDatabaseException_whenPersistFails() {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionParticipants missionParticipants = new MissionParticipants();

		//mock
		doThrow(new HibernateException("test")).when(entityManagerHandler).persist(missionParticipants);

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionParticipantsDao.startMission(missionParticipants, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).persist(missionParticipants);
	}

	@Test
	public void shouldReturnMissionParticipantsListByUserIdAndMissionId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionParticipants missionParticipants = new MissionParticipants();

		List<MissionParticipants> missionParticipantsList = new ArrayList<MissionParticipants>();
		missionParticipantsList.add(missionParticipants);

		User user = new User();
		Mission mission = new Mission();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<MissionParticipants> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM MissionParticipants mp "
				+ "WHERE mp.user.id = :userId AND mp.mission.missionId = :missionId", MissionParticipants.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		doReturn(missionParticipantsList).when(query).getResultList();

		//test
		List<MissionParticipants> result = missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, mission, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM MissionParticipants mp "
				+ "WHERE mp.user.id = :userId AND mp.mission.missionId = :missionId", MissionParticipants.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("missionId", mission.getMissionId());
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowDatabaseException_whenGetMissionParticipantsByUserIdAndMissionIdFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionParticipants missionParticipants = new MissionParticipants();

		List<MissionParticipants> missionParticipantsList = new ArrayList<MissionParticipants>();
		missionParticipantsList.add(missionParticipants);

		User user = new User();
		Mission mission = new Mission();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<MissionParticipants> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM MissionParticipants mp "
				+ "WHERE mp.user.id = :userId AND mp.mission.missionId = :missionId", MissionParticipants.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());

		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionParticipantsDao.getMissionParticipantsByUserIdAndMissionId(user, mission, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM MissionParticipants mp "
				+ "WHERE mp.user.id = :userId AND mp.mission.missionId = :missionId", MissionParticipants.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("missionId", mission.getMissionId());
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldRetriveMissionParticipantsListByUserId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionParticipants missionParticipants = new MissionParticipants();

		List<MissionParticipants> missionParticipantsList = new ArrayList<MissionParticipants>();
		missionParticipantsList.add(missionParticipants);

		int userId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<MissionParticipants> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM MissionParticipants mp "
				+ "WHERE mp.user.id = :userId", MissionParticipants.class);
		doReturn(query).when(query).setParameter("userId", userId);
		doReturn(missionParticipantsList).when(query).getResultList();

		//test
		List<MissionParticipants> result = missionParticipantsDao.getActiveMissionsByUserId(userId, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM MissionParticipants mp "
				+ "WHERE mp.user.id = :userId", MissionParticipants.class);
		verify(query, times(1)).setParameter("userId", userId);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowDatabaseException_whenGetActiveMissionsByUserIdFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionParticipants missionParticipants = new MissionParticipants();

		List<MissionParticipants> missionParticipantsList = new ArrayList<MissionParticipants>();
		missionParticipantsList.add(missionParticipants);

		int userId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<MissionParticipants> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM MissionParticipants mp "
				+ "WHERE mp.user.id = :userId", MissionParticipants.class);
		doReturn(query).when(query).setParameter("userId", userId);

		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionParticipantsDao.getActiveMissionsByUserId(userId, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM MissionParticipants mp "
				+ "WHERE mp.user.id = :userId", MissionParticipants.class);
		verify(query, times(1)).setParameter("userId", userId);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldRemoveParticipant_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		
		User user = new User();
		Mission mission = new Mission();
		
		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<MissionParticipants> query = mock(TypedQuery.class);
		
		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("DELETE FROM MissionParticipants mp " +
                "WHERE mp.user.id = :userId AND mp.mission.id = :missionId");
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		doReturn(1).when(query).executeUpdate();
		
		//test
		missionParticipantsDao.removeParticipant(user, mission, entityManagerHandler);
		
		//result
		verify(entityManager, times(1)).createQuery("DELETE FROM MissionParticipants mp " +
                "WHERE mp.user.id = :userId AND mp.mission.id = :missionId");
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("missionId", mission.getMissionId());
		verify(query, times(1)).executeUpdate();
	}
	
	@Test
	public void shouldThrowDatabaseException_whenRemoveParticipantFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		
		User user = new User();
		Mission mission = new Mission();
		
		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<MissionParticipants> query = mock(TypedQuery.class);
		
		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("DELETE FROM MissionParticipants mp " +
                "WHERE mp.user.id = :userId AND mp.mission.id = :missionId");
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		
		doThrow(new HibernateException("test")).when(query).executeUpdate();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionParticipantsDao.removeParticipant(user, mission, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManager, times(1)).createQuery("DELETE FROM MissionParticipants mp " +
                "WHERE mp.user.id = :userId AND mp.mission.id = :missionId");
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("missionId", mission.getMissionId());
		verify(query, times(1)).executeUpdate();
	}
}
