package com.company.NervManagementConsoleREST.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.model.User;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class MemberStatsAddUtilsTest {
	private MemberStatsAddUtils memberStatsAddUtils = new MemberStatsAddUtils();
	
	@Test
    public void shouldReturnUserMemberStats_whenAllOk() {
        //parameters
        User user = new User();
        Member rei = new Member();
        rei.setName("Rei");
        
        Member asuka = new Member();
        asuka.setName("Asuka");
        
        Member shinji = new Member();
        shinji.setName("Shinji");

        //test
        UserMembersStats resultRei = memberStatsAddUtils.createStatsMembers(user, rei);
        UserMembersStats resultAsuka = memberStatsAddUtils.createStatsMembers(user, asuka);
        UserMembersStats resultShinji = memberStatsAddUtils.createStatsMembers(user, shinji);

        
        //results
        assertNotNull(resultRei);
        assertTrue(resultRei.getStatus());
        assertEquals(0, resultRei.getExp());
        assertEquals(1, resultRei.getLevel());
        assertEquals(25, resultRei.getSynchronizationRate());
        assertEquals(25, resultRei.getTacticalAbility());
        assertEquals(50, resultRei.getSupportAbility());
        
        assertNotNull(resultAsuka);
        assertTrue(resultAsuka.getStatus());
        assertEquals(0, resultAsuka.getExp());
        assertEquals(1, resultAsuka.getLevel());
        assertEquals(30, resultAsuka.getSynchronizationRate());
        assertEquals(50, resultAsuka.getTacticalAbility());
        assertEquals(25, resultAsuka.getSupportAbility());
        
        assertNotNull(resultShinji);
        assertTrue(resultShinji.getStatus());
        assertEquals(0, resultShinji.getExp());
        assertEquals(1, resultShinji.getLevel());
        assertEquals(30, resultShinji.getSynchronizationRate());
        assertEquals(30, resultShinji.getTacticalAbility());
        assertEquals(30, resultShinji.getSupportAbility());
    }

    @Test
    public void shouldReturnDefaultUserMemberStats_whenNameIsUnknown() {
        //parameters
        User user = new User();
        Member unknown = new Member();
        unknown.setName("Unknown");

        //test
        UserMembersStats result = memberStatsAddUtils.createStatsMembers(user, unknown);

        //results
        assertNotNull(result);
        assertTrue(result.getStatus());
        assertEquals(0, result.getExp());
        assertEquals(1, result.getLevel());
        assertEquals(0, result.getSynchronizationRate());
        assertEquals(0, result.getSupportAbility());
        assertEquals(0, result.getTacticalAbility());
    }

}
