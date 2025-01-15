package com.company.NervManagementConsoleREST.dao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.company.NervManagementConsoleREST.dao.atomic.MemberDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;

public class MemberServiceDaoTest {
	private MemberServiceDao memberServiceDao;

	//oggetto mock
	private MemberDao memberDao = mock(MemberDao.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			memberServiceDao = new MemberServiceDao();
		}

		Field memberDaoField = memberServiceDao.getClass().getDeclaredField("memberDao");
		memberDaoField.setAccessible(true);
		memberDaoField.set(memberServiceDao, memberDao);

		Field jpaUtilField = memberServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(memberServiceDao, jpaUtil);
	}

	@Test
	public void shouldReturnMemberList_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		List<Member> memberList = new ArrayList<Member>();
		Member member = new Member();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(memberList).when(memberDao).retrieve(entityManagerHandler);

		//test
		List<Member> result = memberServiceDao.retrieveMembers();

		//result
		assertNotNull(result);
		assertEquals(memberList, result);
		verify(memberDao, times(1)).retrieve(entityManagerHandler);
	}

	@Test
	public void shouldReturnMemberByMemberId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		Member member = new Member();
		int idMember =1;

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(member).when(memberDao).retrieveByMemberId(idMember, entityManagerHandler);

		//test
		Member result = memberServiceDao.retrieveByMemberId(idMember);

		//result
		assertNotNull(result);
		assertEquals(member, result);
		verify(memberDao, times(1)).retrieveByMemberId(idMember, entityManagerHandler);
	}
}
