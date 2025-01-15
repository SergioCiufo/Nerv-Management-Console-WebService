package com.company.NervManagementConsoleREST.model;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class MissionArchiveTest {

    @Test
    public void shouldCreateMissionArchive_whenConstructorIsCalled() {
        // parameters
        String missionCode = "MISSION-001";
        Mission mission = mock(Mission.class); 
        User user = mock(User.class);          
        Member member = mock(Member.class);   
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        Integer tacticalAbility = 80;
        Integer synchRate = 75;
        Integer supportAbility = 90;
        MissionArchive.MissionResult result = MissionArchive.MissionResult.WIN;

        // test
        MissionArchive archive = new MissionArchive(missionCode, mission, user, member, startTime, endTime,
                tacticalAbility, synchRate, supportAbility, result);

        // result
        assertEquals(missionCode, archive.getMissionCode());
        assertEquals(mission, archive.getMission());
        assertEquals(user, archive.getUser());
        assertEquals(member, archive.getMember());
        assertEquals(startTime, archive.getStartTime());
        assertEquals(endTime, archive.getEndTime());
        assertEquals(tacticalAbility, archive.getTacticalAbility());
        assertEquals(synchRate, archive.getSynchRate());
        assertEquals(supportAbility, archive.getSupportAbility());
        assertEquals(result, archive.getResult());
    }

    @Test
    public void shouldSetValuesCorrectly_whenSettersAreCalled() {
        // parameters
        MissionArchive archive = new MissionArchive();
        String missionCode = "MISSION-002";
        Mission mission = mock(Mission.class); 
        User user = mock(User.class);         
        Member member = mock(Member.class);   
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);
        Integer tacticalAbility = 70;
        Integer synchRate = 85;
        Integer supportAbility = 95;
        MissionArchive.MissionResult result = MissionArchive.MissionResult.PROGRESS;

        // test
        archive.setMissionCode(missionCode);
        archive.setMission(mission);
        archive.setUser(user);
        archive.setMember(member);
        archive.setStartTime(startTime);
        archive.setEndTime(endTime);
        archive.setTacticalAbility(tacticalAbility);
        archive.setSynchRate(synchRate);
        archive.setSupportAbility(supportAbility);
        archive.setResult(result);

        // result
        assertEquals(missionCode, archive.getMissionCode());
        assertEquals(mission, archive.getMission());
        assertEquals(user, archive.getUser());
        assertEquals(member, archive.getMember());
        assertEquals(startTime, archive.getStartTime());
        assertEquals(endTime, archive.getEndTime());
        assertEquals(tacticalAbility, archive.getTacticalAbility());
        assertEquals(synchRate, archive.getSynchRate());
        assertEquals(supportAbility, archive.getSupportAbility());
        assertEquals(result, archive.getResult());
    }

    @Test
    public void shouldReturnTrue_whenEqualsIsCalledForEqualObjects() {
        // parameters
        MissionArchive archive1 = new MissionArchive();
        archive1.setMissionArchiveId(1);

        MissionArchive archive2 = new MissionArchive();
        archive2.setMissionArchiveId(1);

        // test
        boolean isEqual = archive1.equals(archive2);

        // result
        assertTrue(isEqual);
    }

    @Test
    public void shouldReturnFalse_whenEqualsIsCalledForDifferentObjects() {
        // parameters
        MissionArchive archive1 = new MissionArchive();
        archive1.setMissionArchiveId(1);

        MissionArchive archive2 = new MissionArchive();
        archive2.setMissionArchiveId(2);

        // test
        boolean isEqual = archive1.equals(archive2);

        // result
        assertFalse(isEqual);
    }

    @Test
    public void shouldReturnHashCode_whenHashCodeIsCalled() {
        // parameters
        MissionArchive archive1 = new MissionArchive();
        archive1.setMissionArchiveId(1);

        MissionArchive archive2 = new MissionArchive();
        archive2.setMissionArchiveId(1);

        // test
        assertEquals(archive1.hashCode(), archive2.hashCode());
    }

    @Test
    public void shouldReturnToString_whenToStringIsCalled() {
        // parameters
        MissionArchive archive = new MissionArchive();
        archive.setMissionArchiveId(1);
        archive.setMissionCode("MISSION-001");

        // test
        String expected = "MissionArchive [missionArchiveId=1, missionCode=MISSION-001, startTime=null, endTime=null, tacticalAbility=null, synchRate=null, supportAbility=null, result=null]";
        assertEquals(expected, archive.toString());
    }
}