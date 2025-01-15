package com.company.NervManagementConsoleREST.dao.atomic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.company.NervManagementConsoleREST.model.MissionParticipants;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.User;

public class SimulationDaoTest {
	private SimulationDao simulationDao = new SimulationDao();

	@Test
	public void shouldReturnSimulationList_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();
		List<Simulation> simulationList = new ArrayList<Simulation>();

		simulationList.add(simulation);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Simulation> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Simulation", Simulation.class);
		doReturn(simulationList).when(query).getResultList();

		//test
		List<Simulation> result = simulationDao.retrieve(entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Simulation", Simulation.class);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();
		List<Simulation> simulationList = new ArrayList<Simulation>();

		simulationList.add(simulation);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Simulation> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Simulation", Simulation.class);
		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			simulationDao.retrieve(entityManagerHandler);
		});
		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Simulation", Simulation.class);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldReturnSimulationBySimulationId_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();

		int simulationId =1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Simulation> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Simulation s WHERE s.simulationId = :simulationId", Simulation.class);
		doReturn(query).when(query).setParameter("simulationId", simulationId);
		doReturn(simulation).when(query).getSingleResult();

		//test
		Simulation result = simulationDao.retrieveBySimulationId(simulationId, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Simulation s WHERE s.simulationId = :simulationId", Simulation.class);
		verify(query, times(1)).setParameter("simulationId", simulationId);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowNoResultException_whenRetrieveSimulationByIdFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();

		int simulationId =1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Simulation> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Simulation s WHERE s.simulationId = :simulationId", Simulation.class);
		doReturn(query).when(query).setParameter("simulationId", simulationId);
		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		Simulation result = simulationDao.retrieveBySimulationId(simulationId, entityManagerHandler);

		//result
		assertNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Simulation s WHERE s.simulationId = :simulationId", Simulation.class);
		verify(query, times(1)).setParameter("simulationId", simulationId);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveSimulationByIdFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();

		int simulationId =1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Simulation> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Simulation s WHERE s.simulationId = :simulationId", Simulation.class);
		doReturn(query).when(query).setParameter("simulationId", simulationId);
		doThrow(new HibernateException("test")).when(query).getSingleResult();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			simulationDao.retrieveBySimulationId(simulationId, entityManagerHandler);
		});
		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Simulation s WHERE s.simulationId = :simulationId", Simulation.class);
		verify(query, times(1)).setParameter("simulationId", simulationId);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldReturnSimulationListByUserId_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();
		List<Simulation> simulationList = new ArrayList<Simulation>();

		simulationList.add(simulation);

		User user = new User();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Simulation> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Simulation s " +
				"JOIN FETCH s.simulationParticipants sp " +
				"WHERE sp.user.id = :userId", Simulation.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(simulationList).when(query).getResultList();

		//test
		List<Simulation> result = simulationDao.getSimulationAndParticipantsByUserId(user, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Simulation s " +
				"JOIN FETCH s.simulationParticipants sp " +
				"WHERE sp.user.id = :userId", Simulation.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowNoResultException_whenGetSimulationAndParticipantsByUserIdFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();
		List<Simulation> simulationList = new ArrayList<Simulation>();

		simulationList.add(simulation);

		User user = new User();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Simulation> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Simulation s " +
				"JOIN FETCH s.simulationParticipants sp " +
				"WHERE sp.user.id = :userId", Simulation.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doThrow(new NoResultException("test")).when(query).getResultList();

		//test
		List<Simulation> result = simulationDao.getSimulationAndParticipantsByUserId(user, entityManagerHandler);

		//result
		assertNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Simulation s " +
				"JOIN FETCH s.simulationParticipants sp " +
				"WHERE sp.user.id = :userId", Simulation.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldDatabaseException_whenGetSimulationAndParticipantsByUserIdFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		Simulation simulation = new Simulation();
		List<Simulation> simulationList = new ArrayList<Simulation>();

		simulationList.add(simulation);

		User user = new User();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<Simulation> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Simulation s " +
				"JOIN FETCH s.simulationParticipants sp " +
				"WHERE sp.user.id = :userId", Simulation.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			simulationDao.getSimulationAndParticipantsByUserId(user, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Simulation s " +
				"JOIN FETCH s.simulationParticipants sp " +
				"WHERE sp.user.id = :userId", Simulation.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).getResultList();
	}
}
