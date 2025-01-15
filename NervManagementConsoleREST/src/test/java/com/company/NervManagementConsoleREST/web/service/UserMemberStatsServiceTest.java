package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.UserMemberStatsServiceDao;
import com.company.NervManagementConsoleREST.dao.service.UserServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;
import com.company.NervManagementConsoleREST.service.UserMemberStatsService;

public class UserMemberStatsServiceTest {
	private UserMemberStatsService userMemberStatsService;
	
	//oggetto mock
	private UserMemberStatsServiceDao userMemberStatsServiceDao = mock(UserMemberStatsServiceDao.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			userMemberStatsService  = new UserMemberStatsService();
	    }
		
		Field f = userMemberStatsService.getClass().getDeclaredField("userMemberStatsServiceDao");
		f.setAccessible(true);
		f.set(userMemberStatsService, userMemberStatsServiceDao);
	}
	
	@Test
	public void shouldCreateStats_whenAllOk() throws Exception{
		//parameters
		User user = new User();
		List<Member> memberList = new ArrayList<Member>();
		
		//mock
		doNothing().when(userMemberStatsServiceDao).saveStats(user, memberList);
		
		//test
		userMemberStatsService.createStatsForDefaultMembers(user, memberList);
		
		//result
		verify(userMemberStatsServiceDao).saveStats(user, memberList);
	}
	
	@Test
	public void shouldReturnUserMemberStatsByUserAndMember_whenAllOk() throws Exception{
		//parameters
		UserMembersStats userMemberStats = new UserMembersStats();
		User user = new User();
		Member member = new Member();
		
		//mock
		doReturn(userMemberStats).when(userMemberStatsServiceDao).retrieveStatsByUserAndMember(user, member);
		
		//test
		UserMembersStats result = userMemberStatsService.retrieveStatsByUserAndMember(user, member);
		
		//result
		verify(userMemberStatsServiceDao).retrieveStatsByUserAndMember(user, member);
		assertNotNull(result);
		assertEquals(userMemberStats, result);
	}
	
	@Test
	public void shouldUpdateMemberStatsStartSim_whenAllOk() throws Exception{
		//parameters
		User user = new User();
		Member member = new Member();
		
		//mocks
		doNothing().when(userMemberStatsServiceDao).updateMembStatsStartSim(user, member);
		
		//test
		userMemberStatsService.updateMembStatsStartSim(user, member);
		
		//result
		verify(userMemberStatsServiceDao).updateMembStatsStartSim(user, member);
	}
	
	@Test
	public void shouldUpdateMemberStatsCompletedSim_whenAllOk() throws Exception{
		//parameters
		UserMembersStats userMembersStats = new UserMembersStats();
		User user = new User();
		Member member = new Member();
		
		//mocks
		doNothing().when(userMemberStatsServiceDao).updateMembStatsCompletedSim(user, member, userMembersStats);
		
		//test
		userMemberStatsService.updateMembStatsCompletedSim(user, member, userMembersStats);
		
		//result
		verify(userMemberStatsServiceDao).updateMembStatsCompletedSim(user, member, userMembersStats);
	}
	
	@Test
	public void shouldUpdateMemberStatsCompletedMission_whenAllOk() throws Exception{
		//parameters
		UserMembersStats userMembersStats = new UserMembersStats();
		
		//mocks
		doNothing().when(userMemberStatsServiceDao).updateMembStatsCompletedMission(userMembersStats);
		
		//test
		userMemberStatsService.updateMembStatsCompletedMission(userMembersStats);
		
		//result
		verify(userMemberStatsServiceDao).updateMembStatsCompletedMission(userMembersStats);
	}
	
	@Test
	public void shouldReturnListUserMemberStatsByUserId_whenAllOk() throws Exception{
		//parameters
		List<UserMembersStats> userMembersStatsList = new ArrayList<UserMembersStats>();
		
		//mocks
		doReturn(userMembersStatsList).when(userMemberStatsServiceDao).retrieveByUserId(anyInt());
		
		//test
		List<UserMembersStats> result = userMemberStatsService.retrieveByUserId(123);
		
		//result
		verify(userMemberStatsServiceDao).retrieveByUserId(anyInt());
		assertNotNull(result);
		assertEquals(userMembersStatsList, result);
	}
}
