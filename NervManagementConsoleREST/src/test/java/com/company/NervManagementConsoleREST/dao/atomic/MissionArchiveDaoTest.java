package com.company.NervManagementConsoleREST.dao.atomic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.MissionArchive;
import com.company.NervManagementConsoleREST.model.MissionArchive.MissionResult;
import com.company.NervManagementConsoleREST.model.User;

public class MissionArchiveDaoTest {
	private MissionArchiveDao missionArchiveDao = new MissionArchiveDao();

	@Test
	public void shouldAddMissionArchive_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionArchive missionArchive = new MissionArchive();

		//mock
		doNothing().when(entityManagerHandler).persist(missionArchive);

		//test
		missionArchiveDao.addMissionArchive(missionArchive, entityManagerHandler);

		//result
		verify(entityManagerHandler, times(1)).persist(missionArchive);
	}

	@Test
	public void shouldDatabaseException_whenPersistFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionArchive missionArchive = new MissionArchive();

		//mock
		doThrow(new HibernateException("test")).when(entityManagerHandler).persist(missionArchive);

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionArchiveDao.addMissionArchive(missionArchive, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).persist(missionArchive);
	}

	@Test
	public void shouldRetriveMissionArchiveListByUserIdMissionId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<MissionArchive> missionArchiveList = new ArrayList<MissionArchive>();
		MissionArchive missionArchive = new MissionArchive();
		missionArchiveList.add(missionArchive);

		User user = new User();
		user.setIdUser(1);
		Mission mission = new Mission();
		mission.setMissionId(1);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM MissionArchive ma WHERE ma.user.id = :userId AND ma.mission.missionId = :missionId", MissionArchive.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		doReturn(missionArchiveList).when(query).getResultList();

		//test
		List<MissionArchive> result = missionArchiveDao.retriveByUserIdAndIdMission(user, mission, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM MissionArchive ma WHERE ma.user.id = :userId AND ma.mission.missionId = :missionId", MissionArchive.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("missionId", mission.getMissionId());
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowDatabaseException_whenNoMissionArchiveFound() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<MissionArchive> missionArchiveList = new ArrayList<MissionArchive>();
		MissionArchive missionArchive = new MissionArchive();
		missionArchiveList.add(missionArchive);

		User user = new User();
		user.setIdUser(1);
		Mission mission = new Mission();
		mission.setMissionId(1);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM MissionArchive ma WHERE ma.user.id = :userId AND ma.mission.missionId = :missionId", MissionArchive.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionArchiveDao.retriveByUserIdAndIdMission(user, mission, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
	}

	@Test
	public void shouldRetrieveMissionArchiveListByUserId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<MissionArchive> missionArchiveList = new ArrayList<MissionArchive>();
		MissionArchive missionArchive = new MissionArchive();
		missionArchiveList.add(missionArchive);

		int userId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM MissionArchive ma WHERE ma.user.id = :userId", MissionArchive.class);
		doReturn(query).when(query).setParameter("userId", userId);
		doReturn(missionArchiveList).when(query).getResultList();

		//test
		List<MissionArchive> result = missionArchiveDao.retriveByUserId(userId, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM MissionArchive ma WHERE ma.user.id = :userId", MissionArchive.class);
		verify(query, times(1)).setParameter("userId", userId);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowDatabaseException_whenNoMissionArchiveFoundByUserId() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<MissionArchive> missionArchiveList = new ArrayList<MissionArchive>();
		MissionArchive missionArchive = new MissionArchive();
		missionArchiveList.add(missionArchive);

		int userId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM MissionArchive ma WHERE ma.user.id = :userId", MissionArchive.class);
		doReturn(query).when(query).setParameter("userId", userId);
		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionArchiveDao.retriveByUserId(userId, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
	}

	@Test
	public void shouldReturnMissionArchiveByUserIdIdMissionProgress_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionArchive missionArchive = new MissionArchive();

		User user = new User();
		Mission mission = new Mission();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery(
				"FROM MissionArchive WHERE user.idUser = :userId AND mission.missionId = :missionId AND result = 'PROGRESS'",
				MissionArchive.class
				);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		doReturn(Collections.singletonList(missionArchive)).when(query).getResultList();

		//test
		MissionArchive result = missionArchiveDao.retriveByUserIdAndIdMissionResultProgress(user, mission, entityManagerHandler);

		//result
		assertNotNull(result);
		assertEquals(missionArchive, result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM MissionArchive WHERE user.idUser = :userId AND mission.missionId = :missionId AND result = 'PROGRESS'", MissionArchive.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("missionId", mission.getMissionId());
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowDatabaseException_whenRetriveMissionArchiveByUserIdMissionIdProgressFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionArchive missionArchive = new MissionArchive();

		User user = new User();
		Mission mission = new Mission();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery(
				"FROM MissionArchive WHERE user.idUser = :userId AND mission.missionId = :missionId AND result = 'PROGRESS'",
				MissionArchive.class
				);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		doThrow(new HibernateException("Simulated Hibernate exception")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionArchiveDao.retriveByUserIdAndIdMissionResultProgress(user, mission, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
	}
	
	@Test
	public void shouldUpdateMissionResult_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		MissionArchive missionArchive = new MissionArchive();
		
		MissionResult missionResult = MissionResult.PROGRESS;
		
		EntityManager entityManager = mock(EntityManager.class);
		Query query = mock(Query.class); //query invece di typedQuery perché l'update non restituisce typedQuery
		
		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE MissionArchive ma SET ma.result = :result "
        		+ "WHERE ma.missionCode = :missionCode");
		doReturn(query).when(query).setParameter("result", missionResult);
		doReturn(query).when(query).setParameter("missionCode", missionArchive.getMissionCode());
		doReturn(1).when(query).executeUpdate();
		
		//test
		missionArchiveDao.updateMissionResult(missionArchive, missionResult, entityManagerHandler);
		
		//result
		verify(query, times(1)).setParameter("result", missionResult);
		verify(query, times(1)).setParameter("missionCode", missionArchive.getMissionCode());
	}
	
	@Test
	public void shouldThrowDatabaseException_whenUpdateMissionResultFails() throws Exception {
	    // Parameters
	    EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
	    MissionArchive missionArchive = new MissionArchive();
	    missionArchive.setMissionArchiveId(1);
	    missionArchive.setMissionCode("test");

	    MissionResult missionResult = MissionResult.PROGRESS;
	    
	    EntityManager entityManager = mock(EntityManager.class);
	    Query query = mock(Query.class);
	    
	    //mock
	    doReturn(entityManager).when(entityManagerHandler).getEntityManager();
	    doReturn(query).when(entityManager).createQuery("UPDATE MissionArchive ma SET ma.result = :result "
	            + "WHERE ma.missionCode = :missionCode");
	    doReturn(query).when(query).setParameter("result", missionResult);
	    doReturn(query).when(query).setParameter("missionCode", missionArchive.getMissionCode());
	    doThrow(new HibernateException("test")).when(query).executeUpdate();

	    //test
	    DatabaseException exception = assertThrows(DatabaseException.class, () -> {
	        missionArchiveDao.updateMissionResult(missionArchive, missionResult, entityManagerHandler);
	    });

	    //result
	    assertNotNull(exception);
	}
}
