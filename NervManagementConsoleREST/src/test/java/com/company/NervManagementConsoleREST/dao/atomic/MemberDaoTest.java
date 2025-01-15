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
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;

public class MemberDaoTest {
	private MemberDao memberDao = new MemberDao();

	@Test
	public void shouldRetrieveMemberList_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<Member> memberList = new ArrayList<Member>();
		Member member = new Member();
		memberList.add(member);

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Member ORDER BY memberId ASC", Member.class);
		doReturn(memberList).when(query).getResultList();

		//test
		List<Member> result =memberDao.retrieve(entityManagerHandler);

		//results
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Member ORDER BY memberId ASC", Member.class);
	}

	@Test
	public void shouldThrowDatabaseException_whenRetrieveFails() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Member ORDER BY memberId ASC", Member.class);

		doThrow(new HibernateException("test")).when(query).getResultList();

		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			memberDao.retrieve(entityManagerHandler);
		});

		//result
		assertNotNull(exception);
	}

	@Test
	public void shouldRetrieveMemberByMemberId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Member member = new Member();
		int memberId=1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Member m WHERE m.idMember = :memberId", Member.class);
		doReturn(query).when(query).setParameter("memberId", memberId);
		doReturn(member).when(query).getSingleResult();

		//test
		Member result =memberDao.retrieveByMemberId(memberId, entityManagerHandler);

		//results
		assertNotNull(result);
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Member m WHERE m.idMember = :memberId", Member.class);
	}

	@Test
	public void shouldThrowNoResultException_whenNoMemberIdFound() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Member member = new Member();
		int memberId=1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Member m WHERE m.idMember = :memberId", Member.class);
		doReturn(query).when(query).setParameter("memberId", memberId);

		doThrow(new NoResultException("test")).when(query).getSingleResult();

		//test
		Member result = memberDao.retrieveByMemberId(memberId, entityManagerHandler);

		//result
		assertNull(result);

		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManagerHandler, times(1)).getEntityManager();
		verify(entityManager, times(1)).createQuery("FROM Member m WHERE m.idMember = :memberId", Member.class);
		verify(query, times(1)).getSingleResult();
	}

	@Test
	public void shouldThrowDatabaseException_whenwhenNoMemberIdFound() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Member member = new Member();
		int memberId=1;

		EntityManager entityManager = mock(EntityManager.class);
		TypedQuery<User> query = mock(TypedQuery.class);

		//mock
		doReturn(entityManager).when(entityManagerHandler).getEntityManager();
		doReturn(query).when(entityManager).createQuery("FROM Member m WHERE m.idMember = :memberId", Member.class);
		doReturn(query).when(query).setParameter("memberId", memberId);
		
		doThrow(new HibernateException("test")).when(query).getSingleResult();
		
		//test
		DatabaseException exception = assertThrows(DatabaseException.class, () -> {
			memberDao.retrieveByMemberId(memberId, entityManagerHandler);
		});
		
		//result
		assertNotNull(exception);
	}
}
