package com.company.NervManagementConsoleREST.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.model.Levelable;
import com.company.NervManagementConsoleREST.model.UserMembersStats;

public class LevelUpUtilsTest {
	private LevelUpUtils levelUpUtils = new LevelUpUtils();
	
	@Test
    public void shouldLevelUp_whenExpExceedsMax() {
        //parameters
        UserMembersStats memberStats = new UserMembersStats();
        memberStats.setLevel(1);
        memberStats.setExp(50);

        Integer newExp = 70;

        //test
        Levelable result = levelUpUtils.levelUp(memberStats, newExp);

        //results
        assertNotNull(result);
        assertEquals(2, result.getLevel());
        assertEquals(20, result.getExp());
    }


	@Test
    public void shouldNotLevelUp_whenExpIsLessThanMax() {
        //parameters
        UserMembersStats memberStats = new UserMembersStats();
        memberStats.setLevel(1);
        memberStats.setExp(30);

        Integer newExp = 50;

        //test
        Levelable result = levelUpUtils.levelUp(memberStats, newExp);

        //results
        assertNotNull(result);
        assertEquals(1, result.getLevel());
        assertEquals(80, result.getExp());
    }

    @Test
    public void shouldLevelUpTo100_whenExpExceedsMax100() {
        //parameters
        UserMembersStats memberStats = new UserMembersStats();
        memberStats.setLevel(99);
        memberStats.setExp(90);

        Integer newExp = 30;

        //test
        Levelable result = levelUpUtils.levelUp(memberStats, newExp);

        //results
        assertNotNull(result);
        assertEquals(100, result.getLevel());
        assertEquals(0, result.getExp());
    }
    
    @Test
    public void shouldLevelUp_whenExpIsNull() {
        //parameters
        UserMembersStats memberStats = new UserMembersStats();
        memberStats.setLevel(1);
        memberStats.setExp(null);

        Integer newExp = 50;

        //test
        Levelable result = levelUpUtils.levelUp(memberStats, newExp);

        //results
        assertNotNull(result);
        assertEquals(1, result.getLevel());
        assertEquals(50, result.getExp());
    }

    @Test
    public void shouldLevelUp_whenLevelIsNull() {
        //parameters
        UserMembersStats memberStats = new UserMembersStats();
        memberStats.setLevel(null);
        memberStats.setExp(30);

        Integer newExp = 50;

        //test
        Levelable result = levelUpUtils.levelUp(memberStats, newExp);

        //results
        assertNotNull(result);
        assertEquals(1, result.getLevel());
        assertEquals(80, result.getExp());
    }

    @Test
    public void shouldLevelUp_whenBothExpAndLevelAreNull() {
        //parameters
        UserMembersStats memberStats = new UserMembersStats();
        memberStats.setLevel(null);
        memberStats.setExp(null);

        Integer newExp = 50;

        //test
        Levelable result = levelUpUtils.levelUp(memberStats, newExp);

        //results
        assertNotNull(result);
        assertEquals(1, result.getLevel());
        assertEquals(50, result.getExp());
    }

    @Test
    public void shouldThrowException_whenEntityIsNull() {
        //parameters
        Integer newExp = 50;

        //test & results
        assertThrows(IllegalArgumentException.class, () -> {
            levelUpUtils.levelUp(null, newExp);
        }, "test");
    }

    @Test
    public void shouldThrowException_whenNewExpIsNull() {
        //parameters
        UserMembersStats memberStats = new UserMembersStats();
        memberStats.setLevel(1);
        memberStats.setExp(50);

        //test & results
        assertThrows(IllegalArgumentException.class, () -> {
            levelUpUtils.levelUp(memberStats, null);
        }, "test");
    }
    
    
}
