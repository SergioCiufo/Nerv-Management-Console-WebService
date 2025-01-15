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
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.SimulationParticipant;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class UserMemberStatsDaoTest {
	private UserMemberStatsDao userMemberStatsDao = new UserMemberStatsDao();

	@Test
	public void shouldCreateUserMemberStats_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		//mocks
		doNothing().when(entityManagerHandler).persist(ums);

		//test
		userMemberStatsDao.create(ums, entityManagerHandler);

		//result
		verify(entityManagerHandler, times(1)).persist(ums);
	}

	@Test
	public void shouldThrowDatabaseException_whenCreateFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User(); //nella throw cerca ums.getUser.etc
		ums.setUser(user); //nella throw cerca ums.getUser.etc

		//mocks
		doThrow(new HibernateException("test")).when(entityManagerHandler).persist(ums);

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userMemberStatsDao.create(ums, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).persist(ums);
	}

	@Test
	public void shouldReturnUserMemberStatsList_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		List<UserMembersStats> umsList = new ArrayList<UserMembersStats>();
		umsList.add(ums);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ORDER BY idMemberStats ASC", UserMembersStats.class);
		doReturn(umsList).when(query).getResultList();

		//test
		List<UserMembersStats> result = userMemberStatsDao.retrieve(entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ORDER BY idMemberStats ASC", UserMembersStats.class);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		List<UserMembersStats> umsList = new ArrayList<UserMembersStats>();
		umsList.add(ums);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ORDER BY idMemberStats ASC", UserMembersStats.class);
		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userMemberStatsDao.retrieve(entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ORDER BY idMemberStats ASC", UserMembersStats.class);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldRetrieveUserMemberStatsListByUserId_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		List<UserMembersStats> umsList = new ArrayList<UserMembersStats>();
		umsList.add(ums);

		int userId=1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", userId);
		doReturn(umsList).when(query).getResultList();

		//test
		List<UserMembersStats> result = userMemberStatsDao.retrieveByUserId(userId, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", userId);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowNoResultException_whenRetrieveByUserIdReturnNull() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		List<UserMembersStats> umsList = new ArrayList<UserMembersStats>();
		umsList.add(ums);

		int userId=1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", userId);
		doThrow(new NoResultException("test")).when(query).getResultList();

		//test
		List<UserMembersStats> result = userMemberStatsDao.retrieveByUserId(userId, entityManagerHandler);

		//result
		assertNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", userId);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveByUserIdFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		List<UserMembersStats> umsList = new ArrayList<UserMembersStats>();
		umsList.add(ums);

		int userId=1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", userId);
		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userMemberStatsDao.retrieveByUserId(userId, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", userId);
		verify(query, times(1)).getResultList();
	}

	@Test
	public void shouldRetrieveUserMemberStatsListByUserAndMember_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doReturn(ums).when(query).getSingleResult();

		//test
		UserMembersStats result = userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowNoResultException_whenRetrieveByUserAndMemberReturnNull() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		UserMembersStats result = userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);

		//result
		assertNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveByUserAndMemberFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doThrow(new HibernateException("test")).when(query).getSingleResult();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userMemberStatsDao.retrieveByUserAndMember(user, member, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldRetrieveUserMemberStatsListByUserAndMemberId_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		int memberId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", memberId);
		doReturn(ums).when(query).getSingleResult();

		//test
		UserMembersStats result = userMemberStatsDao.retrieveByUserAndMemberId(user, memberId, entityManagerHandler);

		//result
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", memberId);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowNoResultException_whenRetrieveByUserAndMemberIdReturnNull() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		int memberId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", memberId);
		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		UserMembersStats result = userMemberStatsDao.retrieveByUserAndMemberId(user, memberId, entityManagerHandler);

		//result
		assertNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", memberId);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveByUserAndMemberIdFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		int memberId = 1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM UserMembersStats ums "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", memberId);
		doThrow(new HibernateException("test")).when(query).getSingleResult();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userMemberStatsDao.retrieveByUserAndMemberId(user, memberId, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM UserMembersStats ums "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId ", UserMembersStats.class);
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", memberId);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldUpdateUserMemberStatsStartSim_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE UserMembersStats ums "
				+ "SET ums.status = :status "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId");
		doReturn(query).when(query).setParameter("status", false);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doReturn(1).when(query).executeUpdate();

		//test
		userMemberStatsDao.updateMembStatsStartSim(user, member, entityManagerHandler);

		//result
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("UPDATE UserMembersStats ums "
				+ "SET ums.status = :status "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId");
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("status", false);
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).executeUpdate();
	}

	@Test
	public void shouldThrowDatabaseException_whenUpdateMembStatsStartSimFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE UserMembersStats ums "
				+ "SET ums.status = :status "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId");
		doReturn(query).when(query).setParameter("status", false);
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doThrow(new HibernateException("test")).when(query).executeUpdate();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			userMemberStatsDao.updateMembStatsStartSim(user, member, entityManagerHandler);
		});

		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("UPDATE UserMembersStats ums "
				+ "SET ums.status = :status "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId");
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("status", false);
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).executeUpdate();
	}

	@Test
	public void shouldUpdateUserMemberStatsCompleteSim_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE UserMembersStats ums "
				+ "SET ums.status = :status, ums.exp = :exp, ums.level = :levelPg, " +
				"ums.synchronizationRate = :sincRate, ums.tacticalAbility = :tactAbility, " +
				"ums.supportAbility = :suppAbility "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId");
		doReturn(query).when(query).setParameter("status", true);
		doReturn(query).when(query).setParameter("exp", ums.getExp());
		doReturn(query).when(query).setParameter("levelPg", ums.getLevel());
		doReturn(query).when(query).setParameter("sincRate", ums.getSynchronizationRate());
		doReturn(query).when(query).setParameter("tactAbility", ums.getTacticalAbility());
		doReturn(query).when(query).setParameter("suppAbility", ums.getSupportAbility());
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doReturn(1).when(query).executeUpdate();

		//test
		userMemberStatsDao.updateMembStatsCompletedSim(user, member, ums, entityManagerHandler);

		//result
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("UPDATE UserMembersStats ums "
				+ "SET ums.status = :status, ums.exp = :exp, ums.level = :levelPg, " +
				"ums.synchronizationRate = :sincRate, ums.tacticalAbility = :tactAbility, " +
				"ums.supportAbility = :suppAbility "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId");
		verify(query, times(1)).setParameter("status", true);
		verify(query, times(1)).setParameter("exp", ums.getExp());
		verify(query, times(1)).setParameter("levelPg", ums.getLevel());
		verify(query, times(1)).setParameter("sincRate", ums.getSynchronizationRate());
		verify(query, times(1)).setParameter("tactAbility", ums.getTacticalAbility());
		verify(query, times(1)).setParameter("suppAbility", ums.getSupportAbility());
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).executeUpdate();
	}
	
	@Test
	public void shouldThrowDatabaseException_whenUpdateMembStatsCompletedSimFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		User user = new User();
		Member member = new Member();

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<UserMembersStats> query = mock(TypedQuery.class);

		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("UPDATE UserMembersStats ums "
				+ "SET ums.status = :status, ums.exp = :exp, ums.level = :levelPg, " +
				"ums.synchronizationRate = :sincRate, ums.tacticalAbility = :tactAbility, " +
				"ums.supportAbility = :suppAbility "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId");
		doReturn(query).when(query).setParameter("status", true);
		doReturn(query).when(query).setParameter("exp", ums.getExp());
		doReturn(query).when(query).setParameter("levelPg", ums.getLevel());
		doReturn(query).when(query).setParameter("sincRate", ums.getSynchronizationRate());
		doReturn(query).when(query).setParameter("tactAbility", ums.getTacticalAbility());
		doReturn(query).when(query).setParameter("suppAbility", ums.getSupportAbility());
		doReturn(query).when(query).setParameter("userId", user.getIdUser());
		doReturn(query).when(query).setParameter("memberId", member.getIdMember());
		doThrow(new HibernateException("test")).when(query).executeUpdate();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
		userMemberStatsDao.updateMembStatsCompletedSim(user, member, ums, entityManagerHandler);
		});
		
		//result
		assertNotNull(exception);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("UPDATE UserMembersStats ums "
				+ "SET ums.status = :status, ums.exp = :exp, ums.level = :levelPg, " +
				"ums.synchronizationRate = :sincRate, ums.tacticalAbility = :tactAbility, " +
				"ums.supportAbility = :suppAbility "
				+ "WHERE ums.user.id = :userId AND ums.member.id = :memberId");
		verify(query, times(1)).setParameter("status", true);
		verify(query, times(1)).setParameter("exp", ums.getExp());
		verify(query, times(1)).setParameter("levelPg", ums.getLevel());
		verify(query, times(1)).setParameter("sincRate", ums.getSynchronizationRate());
		verify(query, times(1)).setParameter("tactAbility", ums.getTacticalAbility());
		verify(query, times(1)).setParameter("suppAbility", ums.getSupportAbility());
		verify(query, times(1)).setParameter("userId", user.getIdUser());
		verify(query, times(1)).setParameter("memberId", member.getIdMember());
		verify(query, times(1)).executeUpdate();
	}
	
	@Test
	public void shouldUpdateUserMembStatsCompleteMission_whenAllOk() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();

		EntityManager entityManager = mock(EntityManager.class);
		
		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(1).when(entityManager).merge(ums);

		//test
		userMemberStatsDao.updateMembStatsCompletedMission(ums, entityManagerHandler);

		//result
	    verify(entityManagerHandler, times(1)).getEntityManager();
	    verify(entityManager, times(1)).merge(ums);
	}
	
	@Test
	public void shouldThrowDatabaseException_whenUpdateMembStatsCompletedMissionFails() throws Exception{
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		UserMembersStats ums = new UserMembersStats();
		
		User user = new User();
		ums.setUser(user);
		
		Member member = new Member();
		ums.setMember(member);

		EntityManager entityManager = mock(EntityManager.class);
		
		//mocks
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doThrow(new HibernateException("test")).when(entityManager).merge(ums);

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
		userMemberStatsDao.updateMembStatsCompletedMission(ums, entityManagerHandler);
		});
		
		//result
		assertNotNull(exception);
	    verify(entityManagerHandler, times(1)).getEntityManager();
	    verify(entityManager, times(1)).merge(ums);
	}
}
