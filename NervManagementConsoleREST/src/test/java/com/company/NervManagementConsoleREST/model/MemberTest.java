package com.company.NervManagementConsoleREST.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MemberTest {

	@Test
	public void shouldReturnCorrectValues_whenConstructedWithParameters() {
	    //parameters
	    Member member = new Member(1, "Shinji", "Ikari", "Third Child");

	    //test
	    assertEquals(1, member.getIdMember());
	    assertEquals("Shinji", member.getName());
	    assertEquals("Ikari", member.getSurname());
	    assertEquals("Third Child", member.getAlias());
	}

	@Test
	public void shouldUpdateValues_whenSettersAreCalled() {
	    //parameters
	    Member member = new Member();

	    //mock
	    member.setIdMember(2);
	    member.setName("Asuka");
	    member.setSurname("Langley");
	    member.setAlias("Second Child");

	    //test
	    assertEquals(2, member.getIdMember());
	    assertEquals("Asuka", member.getName());
	    assertEquals("Langley", member.getSurname());
	    assertEquals("Second Child", member.getAlias());
	}

	@Test
	public void shouldReturnTrueForEqualsAndSameHashCode_whenMembersAreIdentical() {
	    //parameters
	    Member member1 = new Member(1, "Rei", "Ayanami", "First Child");
	    Member member2 = new Member(1, "Rei", "Ayanami", "First Child");

	    //test
	    assertEquals(member1, member2);
	    assertEquals(member1.hashCode(), member2.hashCode());
	}

	@Test
	public void shouldReturnFalseForEqualsAndDifferentHashCode_whenMembersDiffer() {
	    //parameters
	    Member member1 = new Member(1, "Rei", "Ayanami", "First Child");
	    Member member2 = new Member(2, "Kaworu", "Nagisa", "Fifth Child");

	    //test
	    assertNotEquals(member1, member2);
	    assertNotEquals(member1.hashCode(), member2.hashCode());
	}

	@Test
	public void shouldReturnCorrectStringRepresentation_whenToStringIsCalled() {
	    //parameters
	    Member member = new Member(3, "Mari", "Illustrious", "Provisional Child");

	    //result
	    String expected = "Member [idMember=3, name=Mari, surname=Illustrious, alias=Provisional Child]";

	    //test
	    assertEquals(expected, member.toString());
	}

	@Test
	public void shouldReturnCorrectStats_whenMemberHasStats() {
	    //parameters
	    UserMembersStats stats = new UserMembersStats();
	    Member member = new Member(4, "test", "test", "test", stats);

	    //test
	    assertEquals(stats, member.getMemberStats());
	}

	@Test
	public void shouldReturnNull_whenMemberStatsAreSetToNull() {
	    //parameters
	    Member member = new Member(4, "test", "test", "test", null);

	    //test
	    assertNull(member.getMemberStats());
	}
}
