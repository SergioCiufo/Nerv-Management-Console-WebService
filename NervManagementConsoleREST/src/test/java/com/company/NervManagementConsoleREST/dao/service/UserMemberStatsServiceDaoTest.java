package com.company.NervManagementConsoleREST.dao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import com.company.NervManagementConsoleREST.dao.atomic.UserDao;
import com.company.NervManagementConsoleREST.dao.atomic.UserMemberStatsDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.utils.MemberStatsAddUtils;

public class UserMemberStatsServiceDaoTest {
	private UserMemberStatsServiceDao userMemberStatsServiceDao;

	//oggetto mock
	private UserMemberStatsDao userMemberStatsDao = mock(UserMemberStatsDao.class);
	private JpaUtil jpaUtil = mock(JpaUtil.class);
	private MemberStatsAddUtils memberStatsAddUtils = mock(MemberStatsAddUtils.class);

	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			userMemberStatsServiceDao = new UserMemberStatsServiceDao();
		}

		Field userMemberStatsDaoField = userMemberStatsServiceDao.getClass().getDeclaredField("userMemberStatsDao");
		userMemberStatsDaoField.setAccessible(true);
		userMemberStatsDaoField.set(userMemberStatsServiceDao, userMemberStatsDao);

		Field jpaUtilField = userMemberStatsServiceDao.getClass().getDeclaredField("jpaUtil");
		jpaUtilField.setAccessible(true);
		jpaUtilField.set(userMemberStatsServiceDao, jpaUtil);

		Field memberStatsAddUtilsField = userMemberStatsServiceDao.getClass().getDeclaredField("memberStatsAddUtils");
		memberStatsAddUtilsField.setAccessible(true);
		memberStatsAddUtilsField.set(userMemberStatsServiceDao, memberStatsAddUtils);
	}

	@Test
	public void shouldSaveStats_whenAllOk() throws Exception {
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		List<Member> members = new ArrayList<>();

		Member member = new Member();
		member.setIdMember(1);
		member.setName("Rei");

		members.add(member);

		UserMembersStats stats = mock(UserMembersStats.class);

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(stats).when(memberStatsAddUtils).createStatsMembers(user, member);

		doNothing().when(userMemberStatsDao).create(stats, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		userMemberStatsServiceDao.saveStats(user, members);

		//result
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
		verify(memberStatsAddUtils, times(1)).createStatsMembers(user, member);
		verify(userMemberStatsDao, times(1)).create(stats, entityManagerHandler);
	}

	@Test
	public void shouldNotSaveStats_whenIdMemberIsNull() throws Exception {
		//parameter
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();

		Member member = new Member();
		member.setIdMember(null);  //id null per l'if
		member.setName("Rei");

		List<Member> members = new ArrayList<>();
		members.add(member);

		UserMembersStats stats = mock(UserMembersStats.class);

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doNothing().when(userMemberStatsDao).create(stats, entityManagerHandler);
		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		userMemberStatsServiceDao.saveStats(user, members);

		//result
		verify(userMemberStatsDao, times(0)).create(stats, entityManagerHandler);
	}

	@Test
	public void shouldReturnUserMemberStatsByUserAndMember_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();
		Member member = new Member();


		UserMembersStats stats = mock(UserMembersStats.class);

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		doReturn(stats).when(userMemberStatsDao).retrieveByUserAndMember(user, member, entityManagerHandler);


		//test
		UserMembersStats result = userMemberStatsServiceDao.retrieveStatsByUserAndMember(user, member);

		//result
		assertNotNull(result);
		assertEquals(stats, result);
		verify(userMemberStatsDao, times(1)).retrieveByUserAndMember(user, member, entityManagerHandler);
	}

	@Test
	public void shouldUpdateMemberStatsStartSim_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();

		Member member = new Member();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();

		doNothing().when(userMemberStatsDao).updateMembStatsStartSim(user, member, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		userMemberStatsServiceDao.updateMembStatsStartSim(user, member);

		//result
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
		verify(userMemberStatsDao, times(1)).updateMembStatsStartSim(user, member, entityManagerHandler);
	}

	@Test
	public void shouldUpdateMemberStatsCompleteSim_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		User user = new User();

		Member member = new Member();

		UserMembersStats ums = new UserMembersStats();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();

		doNothing().when(userMemberStatsDao).updateMembStatsCompletedSim(user, member, ums, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		userMemberStatsServiceDao.updateMembStatsCompletedSim(user, member, ums);

		//result
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
		verify(userMemberStatsDao, times(1)).updateMembStatsCompletedSim(user, member, ums, entityManagerHandler);
	}

	@Test
	public void shouldUpdateMemberStatsCompleteMission_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);

		UserMembersStats ums = new UserMembersStats();

		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();

		doNothing().when(userMemberStatsDao).updateMembStatsCompletedMission(ums, entityManagerHandler);

		doNothing().when(entityManagerHandler).beginTransaction();
		doNothing().when(entityManagerHandler).commitTransaction();

		//test
		userMemberStatsServiceDao.updateMembStatsCompletedMission(ums);

		//result
		verify(entityManagerHandler, times(1)).beginTransaction();
		verify(entityManagerHandler, times(1)).commitTransaction();
		verify(userMemberStatsDao, times(1)).updateMembStatsCompletedMission(ums, entityManagerHandler);
	}

	@Test
	public void shouldReturnListUserMemberStatsByUserId_whenAllOk() throws Exception{
		//parameters
		EntityManagerHandler entityManagerHandler = mock(EntityManagerHandler.class);
		
		List<UserMembersStats> umsList = new ArrayList<UserMembersStats>();
		UserMembersStats ums = new UserMembersStats();
		umsList.add(ums);
		int userId = 1;
		
		//mock
		doReturn(entityManagerHandler).when(jpaUtil).getEntityManager();
		
		doReturn(umsList).when(userMemberStatsDao).retrieveByUserId(userId, entityManagerHandler);
		
		//test
		List<UserMembersStats> result = userMemberStatsServiceDao.retrieveByUserId(userId);
		
		//result
		assertNotNull(result);
		assertEquals(umsList, result);
		verify(userMemberStatsDao, times(1)).retrieveByUserId(userId, entityManagerHandler);
	}
}
