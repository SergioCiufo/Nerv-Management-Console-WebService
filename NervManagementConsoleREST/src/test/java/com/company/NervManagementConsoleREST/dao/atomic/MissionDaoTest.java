package com.company.NervManagementConsoleREST.dao.atomic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Mission;
import com.company.NervManagementConsoleREST.model.User;

public class MissionDaoTest {
	private MissionDao missionDao = new MissionDao();

	@Test
	public void shouldCreateMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Mission mission = new Mission();

		//mock
		doNothing().when(entityManagerHandler).persist(mission);

		//test
		missionDao.create(mission, entityManagerHandler);

		//result
		verify(entityManagerHandler, times(1)).persist(mission);
	}

	@Test
	public void shouldThrowDatabaseException_whenPersistFails() {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Mission mission = new Mission();

		//mock
		doThrow(new HibernateException("test")).when(entityManagerHandler).persist(mission);

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionDao.create(mission, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).persist(mission);
	}

	@Test
	public void shouldRetrieveMissionList_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<Mission> missionList = new ArrayList<Mission>();
		Mission mission = new Mission();
		missionList.add(mission);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Mission> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Mission ORDER BY missionId ASC", Mission.class);
		doReturn(missionList).when(query).getResultList();

		//test
		List<Mission> result = missionDao.retrieve(entityManagerHandler);

		//results
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Mission ORDER BY missionId ASC", Mission.class);
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveFails() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<Mission> missionList = new ArrayList<Mission>();
		Mission mission = new Mission();
		missionList.add(mission);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Mission> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Mission ORDER BY missionId ASC", Mission.class);

		doThrow(new HibernateException("test")).when(query).getResultList();


		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionDao.retrieve(entityManagerHandler);
		});

		//results
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Mission ORDER BY missionId ASC", Mission.class);
	}

	@Test
	public void shouldReturnMissionByIdMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Mission mission = new Mission();

		int idMission = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Mission> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Mission m WHERE m.missionId = :missionId", Mission.class);
		doReturn(query).when(query).setParameter("missionId", idMission);
		doReturn(mission).when(query).getSingleResult();

		//test
		Mission result = missionDao.getMissionById(idMission, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Mission m WHERE m.missionId = :missionId", Mission.class);
		verify(query, times(1)).setParameter("missionId", idMission);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowNoResultException_whenNoMissionIdFound() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Mission mission = new Mission();

		int idMission =1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Mission> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Mission m WHERE m.missionId = :missionId", Mission.class);
		doReturn(query).when(query).setParameter("missionId", idMission);
		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		Mission result = missionDao.getMissionById(idMission, entityManagerHandler);

		//result
		assertNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Mission m WHERE m.missionId = :missionId", Mission.class);
		verify(query, times(1)).setParameter("missionId", idMission);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowDatabaseException_whenNoMissionIdFound() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Mission mission = new Mission();

		int idMission =1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Mission> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Mission m WHERE m.missionId = :missionId", Mission.class);
		doReturn(query).when(query).setParameter("missionId", idMission);
		doThrow(new HibernateException("test")).when(query).getSingleResult();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionDao.getMissionById(idMission, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Mission m WHERE m.missionId = :missionId", Mission.class);
		verify(query, times(1)).setParameter("missionId", idMission);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldUpdateMission_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Mission mission = new Mission();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Mission> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE Mission m " +
				"SET m.name = :name, " +
				"m.description = :description, " +
				"m.exp = :exp, " +
				"m.level = :levelMin, " +
				"m.synchronizationRate = :synchronizationRate, " +
				"m.tacticalAbility = :tacticalAbility, " +
				"m.supportAbility = :supportAbility, " +
				"m.durationTime = :durationTime, " +
				"m.participantsMax = :participantsMax, " +
				"m.image = :image " +
				"WHERE m.missionId = :missionId");
		doReturn(query).when(query).setParameter("name", mission.getName());
		doReturn(query).when(query).setParameter("description", mission.getDescription());
		doReturn(query).when(query).setParameter("exp", mission.getExp());
		doReturn(query).when(query).setParameter("levelMin", mission.getLevel());
		doReturn(query).when(query).setParameter("synchronizationRate", mission.getSynchronizationRate());
		doReturn(query).when(query).setParameter("tacticalAbility", mission.getTacticalAbility());
		doReturn(query).when(query).setParameter("supportAbility", mission.getSupportAbility());
		doReturn(query).when(query).setParameter("durationTime", mission.getDurationTime());
		doReturn(query).when(query).setParameter("participantsMax", mission.getParticipantsMax());
		doReturn(query).when(query).setParameter("image", mission.getImage());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		doReturn(1).when(query).executeUpdate();

		//test
		missionDao.updateMission(mission, entityManagerHandler);

		//result
		verify(entityManager, times(1)).createQuery("UPDATE Mission m " +
				"SET m.name = :name, " +
				"m.description = :description, " +
				"m.exp = :exp, " +
				"m.level = :levelMin, " +
				"m.synchronizationRate = :synchronizationRate, " +
				"m.tacticalAbility = :tacticalAbility, " +
				"m.supportAbility = :supportAbility, " +
				"m.durationTime = :durationTime, " +
				"m.participantsMax = :participantsMax, " +
				"m.image = :image " +
				"WHERE m.missionId = :missionId");
		verify(query, times(1)).setParameter("name", mission.getName());
		verify(query, times(1)).setParameter("description", mission.getDescription());
		verify(query, times(1)).setParameter("exp", mission.getExp());
		verify(query, times(1)).setParameter("levelMin", mission.getLevel());
		verify(query, times(1)).setParameter("synchronizationRate", mission.getSynchronizationRate());
		verify(query, times(1)).setParameter("tacticalAbility", mission.getTacticalAbility());
		verify(query, times(1)).setParameter("supportAbility", mission.getSupportAbility());
		verify(query, times(1)).setParameter("durationTime", mission.getDurationTime());
		verify(query, times(1)).setParameter("participantsMax", mission.getParticipantsMax());
		verify(query, times(1)).setParameter("image", mission.getImage());
		verify(query, times(1)).setParameter("missionId", mission.getMissionId());

		verify(query, times(1)).executeUpdate();
	}

	@Test
	public void shouldThrowDatabaseException_whenUpdateFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Mission mission = new Mission();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Mission> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE Mission m " +
				"SET m.name = :name, " +
				"m.description = :description, " +
				"m.exp = :exp, " +
				"m.level = :levelMin, " +
				"m.synchronizationRate = :synchronizationRate, " +
				"m.tacticalAbility = :tacticalAbility, " +
				"m.supportAbility = :supportAbility, " +
				"m.durationTime = :durationTime, " +
				"m.participantsMax = :participantsMax, " +
				"m.image = :image " +
				"WHERE m.missionId = :missionId");
		doReturn(query).when(query).setParameter("name", mission.getName());
		doReturn(query).when(query).setParameter("description", mission.getDescription());
		doReturn(query).when(query).setParameter("exp", mission.getExp());
		doReturn(query).when(query).setParameter("levelMin", mission.getLevel());
		doReturn(query).when(query).setParameter("synchronizationRate", mission.getSynchronizationRate());
		doReturn(query).when(query).setParameter("tacticalAbility", mission.getTacticalAbility());
		doReturn(query).when(query).setParameter("supportAbility", mission.getSupportAbility());
		doReturn(query).when(query).setParameter("durationTime", mission.getDurationTime());
		doReturn(query).when(query).setParameter("participantsMax", mission.getParticipantsMax());
		doReturn(query).when(query).setParameter("image", mission.getImage());
		doReturn(query).when(query).setParameter("missionId", mission.getMissionId());
		doReturn(1).when(query).executeUpdate();

		doThrow(new HibernateException("test")).when(query).executeUpdate();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionDao.updateMission(mission, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();

		verify(entityManager, times(1)).createQuery("UPDATE Mission m " +
				"SET m.name = :name, " +
				"m.description = :description, " +
				"m.exp = :exp, " +
				"m.level = :levelMin, " +
				"m.synchronizationRate = :synchronizationRate, " +
				"m.tacticalAbility = :tacticalAbility, " +
				"m.supportAbility = :supportAbility, " +
				"m.durationTime = :durationTime, " +
				"m.participantsMax = :participantsMax, " +
				"m.image = :image " +
				"WHERE m.missionId = :missionId");
		verify(query, times(1)).setParameter("name", mission.getName());
		verify(query, times(1)).setParameter("description", mission.getDescription());
		verify(query, times(1)).setParameter("exp", mission.getExp());
		verify(query, times(1)).setParameter("levelMin", mission.getLevel());
		verify(query, times(1)).setParameter("synchronizationRate", mission.getSynchronizationRate());
		verify(query, times(1)).setParameter("tacticalAbility", mission.getTacticalAbility());
		verify(query, times(1)).setParameter("supportAbility", mission.getSupportAbility());
		verify(query, times(1)).setParameter("durationTime", mission.getDurationTime());
		verify(query, times(1)).setParameter("participantsMax", mission.getParticipantsMax());
		verify(query, times(1)).setParameter("image", mission.getImage());
		verify(query, times(1)).setParameter("missionId", mission.getMissionId());

		verify(query, times(1)).executeUpdate();

	}

	@Test
	public void shouldUpdateEventMission_whenAllOk() throws Exception {
	    // parameter
	    EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

	    EntityManager entityManager = mock(EntityManager.class);
	    TypedQuery<Mission> query = mock(TypedQuery.class);

	    // mock
	    doReturn(entityManager).when(entityManagerHandler).getEntityManager();
	    doReturn(query).when(entityManager).createQuery("UPDATE Mission m SET m.available = false WHERE m.eventMission = true");
	    doReturn(1).when(query).executeUpdate();
	    
	    // test
	    missionDao.updateEventMissionByAvailableTrue(entityManagerHandler);
	    
	    // result
	    verify(entityManager, times(1)).createQuery("UPDATE Mission m SET m.available = false WHERE m.eventMission = true");
	    verify(query, times(1)).executeUpdate();
	}
	
	@Test
	public void shouldThrowDatabaseException_whenUpdateMissionEventFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Mission> query = mock(TypedQuery.class);

		// mock
	    doReturn(entityManager).when(entityManagerHandler).getEntityManager();
	    doReturn(query).when(entityManager).createQuery("UPDATE Mission m SET m.available = false WHERE m.eventMission = true");

		doThrow(new HibernateException("test")).when(query).executeUpdate();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			missionDao.updateEventMissionByAvailableTrue(entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("UPDATE Mission m SET m.available = false WHERE m.eventMission = true");
		verify(query, times(1)).executeUpdate();
	}
}
