package com.company.NervManagementConsoleREST.dao.service;

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
import com.company.NervManagementConsoleREST.dao.atomic.MemberDao;
import com.company.NervManagementConsoleREST.dao.atomic.UserDao;
import com.company.NervManagementConsoleREST.dao.atomic.UserMemberStatsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.utils.MemberStatsAddUtils;

public class RegisterServiceDaoTest {
	private RegisterServiceDao registerServiceDao;

	//oggetto mock
	private UserDao userDao = mock(UserDao.class);
	private MemberDao memberDao = mock(MemberDao.class);
	private UserMemberStatsDao userMemberStatsDao = mock(UserMemberStatsDao.class);
	private MemberStatsAddUtils memberStatsAddUtils = mock(MemberStatsAddUtils.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			registerServiceDao = new RegisterServiceDao();
		}

		Field userDaoField = registerServiceDao.getClass().getDeclaredField("userDao");
		userDaoField.setAccessible(true);
		userDaoField.set(registerServiceDao, userDao);

		Field memberDaoField = registerServiceDao.getClass().getDeclaredField("memberDao");
		memberDaoField.setAccessible(true);
		memberDaoField.set(registerServiceDao, memberDao);

		Field userMemberStatsDaoField = registerServiceDao.getClass().getDeclaredField("userMemberStatsDao");
		userMemberStatsDaoField.setAccessible(true);
		userMemberStatsDaoField.set(registerServiceDao, userMemberStatsDao);

		Field memberStatsAddUtilsField = registerServiceDao.getClass().getDeclaredField("memberStatsAddUtils");
		memberStatsAddUtilsField.setAccessible(true);
		memberStatsAddUtilsField.set(registerServiceDao, memberStatsAddUtils);

		Field jpaUtilField = registerServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(registerServiceDao, jpaUtil);
	}

	@Test
	public void shouldRegisterUser_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		User user = new User();
		user.setUsername("username");

		Member member = new Member();
		member.setIdMember(1);
		member.setName("Rei");

		UserMembersStats stats = mock(UserMembersStats.class);

		List<Member> memberList = new ArrayList<Member>();
		memberList.add(member);

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(memberList).when(memberDao).retrieve(entityManagerHandler);
		doNothing().when(userDao).create(user, entityManagerHandler);
		doReturn(user).when(userDao).getUserByUsername(user.getUsername(), entityManagerHandler);

		doReturn(stats).when(memberStatsAddUtils).createStatsMembers(user, member);

		doNothing().when(userMemberStatsDao).create(stats, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		registerServiceDao.registerUser(user);

		//result
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();

		verify(memberStatsAddUtils, times(1)).createStatsMembers(user, member);
		verify(userMemberStatsDao, times(1)).create(stats, entityManagerHandler);
	}
	
	@Test
	public void shouldNotRegisterUser_whenIdMemberIsNull() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		User user = new User();
		user.setUsername("username");

		Member member = new Member();
		member.setIdMember(null); //id null per l'if
		member.setName("Rei");

		UserMembersStats stats = mock(UserMembersStats.class);

		List<Member> memberList = new ArrayList<Member>();
		memberList.add(member);

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(memberList).when(memberDao).retrieve(entityManagerHandler);
		doNothing().when(userDao).create(user, entityManagerHandler);
		doReturn(user).when(userDao).getUserByUsername(user.getUsername(), entityManagerHandler);

		doReturn(stats).when(memberStatsAddUtils).createStatsMembers(user, member);

		doNothing().when(userMemberStatsDao).create(stats, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		registerServiceDao.registerUser(user);

		//result
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();

		verify(userMemberStatsDao, times(0)).create(stats, entityManagerHandler);
	}
}
