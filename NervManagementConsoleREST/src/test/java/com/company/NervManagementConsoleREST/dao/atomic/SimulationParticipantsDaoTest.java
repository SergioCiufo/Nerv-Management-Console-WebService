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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.exception.DatabaseException;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.Simulation;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;

public class SimulationParticipantsDaoTest {
	private SimulationParticipantsDao simulationParticipantsDao = new SimulationParticipantsDao();

	@Test
	public void shouldCreateParticipant_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		SimulationParticipant simulationParticipant = new SimulationParticipant();

		//mocks
		doNothing().when(entityManagerHandler).persist(simulationParticipant);

		//test
		simulationParticipantsDao.createParticipant(simulationParticipant, entityManagerHandler);

		//result
		verify(entityManagerHandler, times(1)).persist(simulationParticipant);
	}

	@Test
	public void shouldThrowDatabaseException_whenCreateParticipantFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		SimulationParticipant simulationParticipant = new SimulationParticipant();

		//mocks
		doThrow(new HibernateException("test")).when(entityManagerHandler).persist(simulationParticipant);

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			simulationParticipantsDao.createParticipant(simulationParticipant, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).persist(simulationParticipant);
	}

	@Test
	public void shouldReturnSimulationParticipantByUserAndMemberId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		SimulationParticipant simulationParticipant = new SimulationParticipant();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<SimulationParticipant> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM SimulationParticipant sp "
				+ "WHERE sp.user.id = :userId AND sp.member.id = :memberId", SimulationParticipant.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doReturn(simulationParticipant).when(query).getSingleResult();

		//test
		SimulationParticipant result = simulationParticipantsDao.getParticipantbyUserAndMemberId(user, member, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM SimulationParticipant sp "
				+ "WHERE sp.user.id = :userId AND sp.member.id = :memberId", SimulationParticipant.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowNoResultException_whenGetParticipantbyUserAndMemberIdFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		SimulationParticipant simulationParticipant = new SimulationParticipant();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<SimulationParticipant> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM SimulationParticipant sp "
				+ "WHERE sp.user.id = :userId AND sp.member.id = :memberId", SimulationParticipant.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		SimulationParticipant result = simulationParticipantsDao.getParticipantbyUserAndMemberId(user, member, entityManagerHandler);

		//result
		assertNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM SimulationParticipant sp "
				+ "WHERE sp.user.id = :userId AND sp.member.id = :memberId", SimulationParticipant.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowDatabaseException_whenGetParticipantbyUserAndMemberIdFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		SimulationParticipant simulationParticipant = new SimulationParticipant();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<SimulationParticipant> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM SimulationParticipant sp "
				+ "WHERE sp.user.id = :userId AND sp.member.id = :memberId", SimulationParticipant.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doThrow(new HibernateException("test")).when(query).getSingleResult();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			simulationParticipantsDao.getParticipantbyUserAndMemberId(user, member, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM SimulationParticipant sp "
				+ "WHERE sp.user.id = :userId AND sp.member.id = :memberId", SimulationParticipant.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldRemoveParticipant_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		SimulationParticipant simulationParticipant = new SimulationParticipant();

		User user = new User();
		Simulation simulation = new Simulation();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<SimulationParticipant> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("DELETE FROM SimulationParticipant sp " +
				"WHERE sp.user.id = :userId AND sp.simulation.id = :simulationId");
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("simulationId", simulation.getSimulationId());
		doReturn(1).when(query).executeUpdate();

		//test
		simulationParticipantsDao.removeParticipant(user, simulation, entityManagerHandler);

		//result
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("DELETE FROM SimulationParticipant sp " +
				"WHERE sp.user.id = :userId AND sp.simulation.id = :simulationId");
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("simulationId", simulation.getSimulationId());
		verify(query, times(1)).executeUpdate();
	}

	@Test
	public void shouldThrowDatabaseException_whenRemoveParticipantFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		SimulationParticipant simulationParticipant = new SimulationParticipant();

		User user = new User();
		Simulation simulation = new Simulation();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<SimulationParticipant> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("DELETE FROM SimulationParticipant sp " +
				"WHERE sp.user.id = :userId AND sp.simulation.id = :simulationId");
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("simulationId", simulation.getSimulationId());
		doThrow(new HibernateException("test")).when(query).executeUpdate();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			simulationParticipantsDao.removeParticipant(user, simulation, entityManagerHandler);
		});
		
		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("DELETE FROM SimulationParticipant sp " +
				"WHERE sp.user.id = :userId AND sp.simulation.id = :simulationId");
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("simulationId", simulation.getSimulationId());
		verify(query, times(1)).executeUpdate();
	}
}
