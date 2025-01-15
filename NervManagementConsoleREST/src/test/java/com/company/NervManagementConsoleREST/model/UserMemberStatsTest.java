package com.company.NervManagementConsoleREST.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

public class UserMemberStatsTest {
	@Test
	void shouldCreateUserMembersStatsWithCorrectValues_whenValidParametersProvided() {
		//parameters
		User userMock = mock(User.class);
		Member memberMock = mock(Member.class);
		boolean status = true;
		int exp = 100;
		int level = 5;
		int synchronizationRate = 80;
		int tacticalAbility = 70;
		int supportAbility = 60;

		// mocks
		userMock = mock(User.class);
		memberMock = mock(Member.class);

		//test
		UserMembersStats userMembersStats = new UserMembersStats(status, exp, level, synchronizationRate, tacticalAbility, supportAbility, userMock, memberMock);

		//results
		assertNotNull(userMembersStats);
		assertEquals(status, userMembersStats.getStatus());
		assertEquals(exp, userMembersStats.getExp());
		assertEquals(level, userMembersStats.getLevel());
		assertEquals(synchronizationRate, userMembersStats.getSynchronizationRate());
		assertEquals(tacticalAbility, userMembersStats.getTacticalAbility());
		assertEquals(supportAbility, userMembersStats.getSupportAbility());
		assertEquals(userMock, userMembersStats.getUser());
		assertEquals(memberMock, userMembersStats.getMember());
	}

	@Test
	void shouldCalculateHashCodeCorrectly_whenUserMembersStatsAreCreated() {
		//parameters
		User userMock = mock(User.class);
		Member memberMock = mock(Member.class);
		boolean status = true;
		int exp = 100;
		int level = 5;
		int synchronizationRate = 80;
		int tacticalAbility = 70;
		int supportAbility = 60;

		//mocks
		userMock = mock(User.class);
		memberMock = mock(Member.class);

		//test
		UserMembersStats userMembersStats = new UserMembersStats(status, exp, level, synchronizationRate, tacticalAbility, supportAbility, userMock, memberMock);
		UserMembersStats anotherStats = new UserMembersStats(status, exp, level, synchronizationRate, tacticalAbility, supportAbility, userMock, memberMock);

		//results
		assertEquals(userMembersStats.hashCode(), anotherStats.hashCode());
	}

	@Test
	void shouldReturnTrue_whenUserMembersStatsAreEqual() {
		//parameters
		User userMock = mock(User.class);
		Member memberMock = mock(Member.class);
		boolean status = true;
		int exp = 100;
		int level = 5;
		int synchronizationRate = 80;
		int tacticalAbility = 70;
		int supportAbility = 60;

		//mocks
		userMock = mock(User.class);
		memberMock = mock(Member.class);

		//test
		UserMembersStats userMembersStats = new UserMembersStats(status, exp, level, synchronizationRate, tacticalAbility, supportAbility, userMock, memberMock);
		UserMembersStats otherStats = new UserMembersStats(status, exp, level, synchronizationRate, tacticalAbility, supportAbility, userMock, memberMock);

		//results
		assertTrue(userMembersStats.equals(otherStats));
	}

	@Test
	void shouldReturnCorrectString_whenToStringIsCalled() {
		//parameters
		User userMock = mock(User.class);
		Member memberMock = mock(Member.class);
		boolean status = true;
		int exp = 100;
		int level = 5;
		int synchronizationRate = 80;
		int tacticalAbility = 70;
		int supportAbility = 60;

		//mocks
		userMock = mock(User.class);
		memberMock = mock(Member.class);

		//test
		UserMembersStats userMembersStats = new UserMembersStats(status, exp, level, synchronizationRate, tacticalAbility, supportAbility, userMock, memberMock);

		//results
		String expected = "MemberStats [idMemberStats=null, user=com.company.NervManagementConsoleREST.model.User@..., member=com.company.NervManagementConsoleREST.model.Member@..., status =true toString()=SuperClass toString]";
		assertTrue(userMembersStats.toString().contains("status =true"));
	}
}
